// ─── Config ───
const API = 'http://localhost:8080';
let TOKEN = localStorage.getItem('sc_token') || '';
let ROLE = localStorage.getItem('sc_role') || '';
let USER_EMAIL = localStorage.getItem('sc_email') || '';
let allStudents = [], allTeachers = [], allCourses = [], allBooks = [];
let orderItems = [];

// ─── API Helper ───
async function api(method, path, body) {
  const opts = {
    method,
    headers: { 'Content-Type': 'application/json', 'Authorization': TOKEN ? `Bearer ${TOKEN}` : '' }
  };
  if (body) opts.body = JSON.stringify(body);
  try {
    const r = await fetch(API + path, opts);
    if (r.status === 204) return null;
    const data = await r.json();
    if (!r.ok) throw new Error(data.message || `Error ${r.status}`);
    return data;
  } catch (e) {
    toast(e.message || 'Network error – is the backend running?', 'error');
    throw e;
  }
}

// ─── Toast ───
function toast(msg, type='info') {
  const c = document.getElementById('toast-container');
  const t = document.createElement('div');
  t.className = `toast ${type}`;
  const icons = {success:'✅',error:'❌',info:'ℹ️'};
  t.innerHTML = `<span>${icons[type]||'ℹ️'}</span><span>${msg}</span>`;
  c.appendChild(t);
  setTimeout(() => t.remove(), 4000);
}

// ─── Modal ───
function openModal(id) { document.getElementById(id).classList.add('open') }
function closeModal(id) { document.getElementById(id).classList.remove('open') }
document.querySelectorAll('.modal-overlay').forEach(m => m.addEventListener('click', e => { if(e.target===m) m.classList.remove('open') }));

// ─── Tabs ───
function switchTab(el, panelId) {
  const container = el.closest('.tabs');
  container.querySelectorAll('.tab').forEach(t=>t.classList.remove('active'));
  el.classList.add('active');
  let p = container.nextElementSibling;
  while(p && p.classList.contains('tab-panel')){
    p.classList.remove('active');
    p = p.nextElementSibling;
  }
  document.getElementById(panelId).classList.add('active');
}

// ─── Sidebar ───
function openSidebar(){document.getElementById('sidebar').classList.add('open');document.getElementById('overlay').classList.add('open')}
function closeSidebar(){document.getElementById('sidebar').classList.remove('open');document.getElementById('overlay').classList.remove('open')}

// ─── Auth ───
function showRegister(){document.getElementById('login-form').style.display='none';document.getElementById('register-form').style.display='block'}
function showLogin(){document.getElementById('register-form').style.display='none';document.getElementById('login-form').style.display='block'}

async function doLogin() {
  const email = document.getElementById('login-email').value;
  const password = document.getElementById('login-password').value;
  if (!email||!password){toast('Please fill all fields','error');return}
  const btn = document.getElementById('login-btn');
  btn.innerHTML = '<span class="spinner"></span>';
  try {
    const data = await api('POST','/api/auth/login',{email,password});
    TOKEN = data.token; ROLE = data.role; USER_EMAIL = email;
    localStorage.setItem('sc_token',TOKEN);
    localStorage.setItem('sc_role',ROLE);
    localStorage.setItem('sc_email',email);
    initApp();
  } finally { btn.innerHTML='Sign In' }
}

async function doRegister() {
  const username = document.getElementById('reg-username').value;
  const email = document.getElementById('reg-email').value;
  const password = document.getElementById('reg-password').value;
  const role = document.getElementById('reg-role').value;
  if(!username||!email||!password){toast('Please fill all fields','error');return}
  await api('POST','/api/auth/register',{username,email,password,role});
  toast('Account created! Please sign in.','success');
  showLogin();
}

function doLogout(){
  TOKEN='';ROLE='';USER_EMAIL='';
  localStorage.removeItem('sc_token');localStorage.removeItem('sc_role');localStorage.removeItem('sc_email');
  document.getElementById('app').style.display='none';
  document.getElementById('auth-screen').style.display='flex';
}

// ─── Navigation ───
const NAV_CONFIG = {
  ADMIN: [
    {label:'Overview', icon:'⊞', items:[{id:'dashboard',label:'Dashboard',icon:'🏠'}]},
    {label:'People', icon:'', items:[{id:'students',label:'Students',icon:'🎓'},{id:'teachers',label:'Teachers',icon:'👨‍🏫'},{id:'users',label:'Users',icon:'👤'}]},
    {label:'Academic', icon:'', items:[{id:'courses',label:'Courses',icon:'📚'},{id:'enrollments',label:'Enrollments',icon:'📌'},{id:'schedule',label:'Schedule',icon:'📅'},{id:'attendance',label:'Attendance',icon:'✅'},{id:'exams',label:'Exams',icon:'📝'}]},
    {label:'Campus', icon:'', items:[{id:'library',label:'Library',icon:'📖'},{id:'canteen',label:'Canteen',icon:'🍽️'},{id:'fees',label:'Fees',icon:'💳'},{id:'notices',label:'Notices',icon:'📢'}]},
  ],
  TEACHER: [
    {label:'Overview', icon:'', items:[{id:'dashboard',label:'Dashboard',icon:'🏠'}]},
    {label:'Academic', icon:'', items:[{id:'courses',label:'My Courses',icon:'📚'},{id:'attendance',label:'Attendance',icon:'✅'},{id:'exams',label:'Exams',icon:'📝'},{id:'schedule',label:'Schedule',icon:'📅'}]},
    {label:'Campus', icon:'', items:[{id:'notices',label:'Notices',icon:'📢'},{id:'library',label:'Library',icon:'📖'}]},
  ],
  STUDENT: [
    {label:'Overview', icon:'', items:[{id:'dashboard',label:'Dashboard',icon:'🏠'}]},
    {label:'Academic', icon:'', items:[{id:'courses',label:'Courses',icon:'📚'},{id:'enrollments',label:'My Enrollments',icon:'📌'},{id:'attendance',label:'Attendance',icon:'✅'},{id:'exams',label:'Exam Results',icon:'📝'},{id:'schedule',label:'Schedule',icon:'📅'}]},
    {label:'Campus', icon:'', items:[{id:'library',label:'Library',icon:'📖'},{id:'canteen',label:'Canteen',icon:'🍽️'},{id:'fees',label:'My Fees',icon:'💳'},{id:'notices',label:'Notices',icon:'📢'}]},
  ]
};

function buildNav(){
  const nav = document.getElementById('sidebar-nav');
  nav.innerHTML='';
  const config = NAV_CONFIG[ROLE] || NAV_CONFIG.STUDENT;
  config.forEach(section=>{
    const lbl = document.createElement('div');
    lbl.className='nav-section-label';
    lbl.textContent=section.label;
    nav.appendChild(lbl);
    section.items.forEach(item=>{
      const el = document.createElement('div');
      el.className='nav-item';
      el.dataset.page=item.id;
      el.innerHTML=`<span class="nav-icon">${item.icon}</span><span>${item.label}</span>`;
      el.onclick=()=>navigateTo(item.id, item.label);
      nav.appendChild(el);
    });
  });
}

function navigateTo(pageId, label='') {
  document.querySelectorAll('.page').forEach(p=>p.classList.remove('active'));
  document.querySelectorAll('.nav-item').forEach(n=>n.classList.remove('active'));
  const page = document.getElementById('page-'+pageId);
  if(page) page.classList.add('active');
  document.querySelectorAll(`.nav-item[data-page="${pageId}"]`).forEach(n=>n.classList.add('active'));
  document.getElementById('topbar-title').textContent = label || pageId;
  closeSidebar();
  const loaders = {
    students:loadStudents, teachers:loadTeachers, courses:loadCourses,
    library:loadBooks, canteen:loadMenu, notices:loadNotices,
    users:loadUsers, schedule:buildScheduleGrid
  };
  if(loaders[pageId]) loaders[pageId]();
}

function initApp(){
  document.getElementById('auth-screen').style.display='none';
  document.getElementById('app').style.display='flex';
  const initials = USER_EMAIL.charAt(0).toUpperCase();
  document.getElementById('user-avatar').textContent = initials;
  document.getElementById('user-name-display').textContent = USER_EMAIL.split('@')[0];
  document.getElementById('user-role-display').textContent = ROLE;
  const badge = document.getElementById('topbar-badge');
  badge.textContent = ROLE;
  badge.className = `badge badge-${ROLE.toLowerCase()}`;
  buildNav();
  loadDashboard();
  navigateTo('dashboard','Dashboard');
}

// ─── Dashboard ───
async function loadDashboard(){
  const greeting = ['morning','afternoon','evening'][Math.floor(new Date().getHours()/8)] || 'day';
  document.getElementById('dash-greeting').textContent = `Good ${greeting} 👋`;
  try {
    const [students, teachers, courses, notices] = await Promise.allSettled([
      api('GET','/api/students'), api('GET','/api/teachers'),
      api('GET','/api/courses'), api('GET','/api/notices')
    ]);
    const s = students.value||[], t = teachers.value||[], c = courses.value||[], n = notices.value||[];
    allStudents=s; allTeachers=t; allCourses=c;
    const statsEl = document.getElementById('dash-stats');
    statsEl.innerHTML = `
      <div class="stat-card" style="--accent-color:var(--accent)">
        <div class="stat-icon">🎓</div>
        <div class="stat-value">${s.length}</div>
        <div class="stat-label">Students</div>
      </div>
      <div class="stat-card" style="--accent-color:var(--accent2)">
        <div class="stat-icon">👨‍🏫</div>
        <div class="stat-value">${t.length}</div>
        <div class="stat-label">Teachers</div>
      </div>
      <div class="stat-card" style="--accent-color:var(--accent3)">
        <div class="stat-icon">📚</div>
        <div class="stat-value">${c.length}</div>
        <div class="stat-label">Courses</div>
      </div>
      <div class="stat-card" style="--accent-color:var(--danger)">
        <div class="stat-icon">📢</div>
        <div class="stat-value">${n.length}</div>
        <div class="stat-label">Notices</div>
      </div>
    `;
    const noticeEl = document.getElementById('dash-notices');
    if(n.length){
      noticeEl.innerHTML = n.slice(0,3).map(notice=>`
        <div class="notice-item">
          <h4>${esc(notice.title)}</h4>
          <p>${esc(notice.content?.substring(0,100))}${notice.content?.length>100?'...':''}</p>
          <div class="notice-meta">📅 ${notice.postedDate?.split('T')[0]||'—'} · by ${esc(notice.postedByName||'Staff')}</div>
        </div>
      `).join('');
    } else { noticeEl.innerHTML='<div class="empty-state"><div class="empty-icon">📭</div><p>No notices yet</p></div>' }
    document.getElementById('dash-quick').innerHTML=`
      <div style="display:flex;flex-direction:column;gap:12px">
        <div style="display:flex;justify-content:space-between;align-items:center;padding:10px;background:var(--surface2);border-radius:8px">
          <span style="font-size:.85rem;color:var(--text2)">Today</span>
          <span style="font-size:.85rem;font-weight:600">${new Date().toLocaleDateString('en-EG',{weekday:'long',month:'short',day:'numeric'})}</span>
        </div>
        <div style="display:flex;justify-content:space-between;align-items:center;padding:10px;background:var(--surface2);border-radius:8px">
          <span style="font-size:.85rem;color:var(--text2)">Role</span>
          <span class="chip chip-${ROLE==='ADMIN'?'yellow':ROLE==='TEACHER'?'blue':'green'}">${ROLE}</span>
        </div>
        <div style="display:flex;justify-content:space-between;align-items:center;padding:10px;background:var(--surface2);border-radius:8px">
          <span style="font-size:.85rem;color:var(--text2)">API Status</span>
          <span class="chip chip-green">● Connected</span>
        </div>
      </div>
    `;
  } catch(e){}
}

// ─── Students ───
async function loadStudents(){
  try { allStudents = await api('GET','/api/students'); renderStudents(allStudents) } catch(e){}
}
function renderStudents(data){
  const tbody = document.getElementById('student-tbody');
  if(!data?.length){tbody.innerHTML='<tr><td colspan="6"><div class="empty-state"><div class="empty-icon">🎓</div><p>No students found</p></div></td></tr>';return}
  tbody.innerHTML = data.map(s=>`
    <tr>
      <td>${s.studentId}</td>
      <td><strong>${esc(s.firstName)} ${esc(s.lastName)}</strong></td>
      <td><code style="background:var(--surface2);padding:2px 8px;border-radius:4px;font-size:.8rem">${esc(s.rollNo||'—')}</code></td>
      <td>${esc(s.department)}</td>
      <td>${esc(s.batch||'—')}</td>
      <td><button class="btn btn-danger btn-sm" onclick="deleteStudent(${s.studentId})">🗑</button></td>
    </tr>
  `).join('');
}
function filterStudents(){
  const q = document.getElementById('student-search').value.toLowerCase();
  renderStudents(allStudents.filter(s=>`${s.firstName} ${s.lastName} ${s.rollNo} ${s.department}`.toLowerCase().includes(q)));
}
async function createStudent(){
  const dto = {firstName:v('s-fname'),lastName:v('s-lname'),department:v('s-dept'),batch:v('s-batch'),rollNo:v('s-roll'),userId:num('s-uid')};
  try { await api('POST','/api/students',dto); toast('Student created!','success'); closeModal('modal-student'); loadStudents() } catch(e){}
}
async function deleteStudent(id){
  if(!confirm('Delete this student?'))return;
  try { await api('DELETE',`/api/students/${id}`); toast('Student deleted','success'); loadStudents() } catch(e){}
}

// ─── Teachers ───
async function loadTeachers(){
  try { allTeachers = await api('GET','/api/teachers'); renderTeachers(allTeachers) } catch(e){}
}
function renderTeachers(data){
  const tbody = document.getElementById('teacher-tbody');
  if(!data?.length){tbody.innerHTML='<tr><td colspan="6"><div class="empty-state"><div class="empty-icon">👨‍🏫</div><p>No teachers found</p></div></td></tr>';return}
  tbody.innerHTML = data.map(t=>`
    <tr>
      <td>${t.teacherId}</td>
      <td><strong>${esc(t.firstName)} ${esc(t.lastName)}</strong></td>
      <td>${esc(t.department)}</td>
      <td><span class="chip chip-blue">${esc(t.qualification)}</span></td>
      <td>${esc(t.phone||'—')}</td>
      <td><button class="btn btn-danger btn-sm" onclick="deleteTeacher(${t.teacherId})">🗑</button></td>
    </tr>
  `).join('');
}
function filterTeachers(){
  const q = document.getElementById('teacher-search').value.toLowerCase();
  renderTeachers(allTeachers.filter(t=>`${t.firstName} ${t.lastName} ${t.department}`.toLowerCase().includes(q)));
}
async function createTeacher(){
  const dto = {firstName:v('t-fname'),lastName:v('t-lname'),department:v('t-dept'),qualification:v('t-qual'),phone:v('t-phone'),userId:num('t-uid')};
  try { await api('POST','/api/teachers',dto); toast('Teacher created!','success'); closeModal('modal-teacher'); loadTeachers() } catch(e){}
}
async function deleteTeacher(id){
  if(!confirm('Delete this teacher?'))return;
  try { await api('DELETE',`/api/teachers/${id}`); toast('Teacher deleted','success'); loadTeachers() } catch(e){}
}

// ─── Courses ───
async function loadCourses(){
  try { allCourses = await api('GET','/api/courses'); renderCourses(allCourses) } catch(e){}
}
function renderCourses(data){
  const tbody = document.getElementById('course-tbody');
  if(!data?.length){tbody.innerHTML='<tr><td colspan="6"><div class="empty-state"><div class="empty-icon">📚</div><p>No courses found</p></div></td></tr>';return}
  tbody.innerHTML = data.map(c=>`
    <tr>
      <td><code style="background:var(--surface2);padding:2px 8px;border-radius:4px;font-size:.8rem;color:var(--accent)">${esc(c.courseCode)}</code></td>
      <td><strong>${esc(c.courseName)}</strong></td>
      <td>${esc(c.department)}</td>
      <td><span class="chip chip-yellow">${c.credits} cr</span></td>
      <td>ID: ${c.teacherId}</td>
      <td><button class="btn btn-danger btn-sm" onclick="deleteCourse(${c.id})">🗑</button></td>
    </tr>
  `).join('');
}
function filterCourses(){
  const q = document.getElementById('course-search').value.toLowerCase();
  renderCourses(allCourses.filter(c=>`${c.courseName} ${c.courseCode} ${c.department}`.toLowerCase().includes(q)));
}
async function createCourse(){
  const dto={courseName:v('c-name'),courseCode:v('c-code'),credits:num('c-credits'),department:v('c-dept'),teacherId:num('c-tid')};
  try { await api('POST','/api/courses',dto); toast('Course created!','success'); closeModal('modal-course'); loadCourses() } catch(e){}
}
async function deleteCourse(id){
  if(!confirm('Delete this course?'))return;
  try { await api('DELETE',`/api/courses/${id}`); toast('Course deleted','success'); loadCourses() } catch(e){}
}

// ─── Attendance ───
async function markAttendance(){
  const dto={studentId:num('a-sid'),courseId:num('a-cid'),date:v('a-date'),status:v('a-status')};
  try { await api('POST','/api/attendance',dto); toast('Attendance marked!','success'); closeModal('modal-attendance') } catch(e){}
}
async function loadStudentAttendance(){
  const id = document.getElementById('att-student-id').value;
  if(!id){toast('Enter Student ID','error');return}
  try {
    const data = await api('GET',`/api/attendance/student/${id}`);
    const tbody = document.getElementById('att-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="3"><div class="empty-state"><div class="empty-icon">✅</div><p>No records</p></div></td></tr>';return}
    tbody.innerHTML = data.map(a=>`
      <tr>
        <td>${a.date}</td>
        <td>Course ${a.courseId}</td>
        <td><span class="chip ${a.status==='PRESENT'?'chip-green':a.status==='ABSENT'?'chip-red':a.status==='LATE'?'chip-yellow':'chip-blue'}">${a.status}</span></td>
      </tr>
    `).join('');
  } catch(e){}
}

// ─── Exams ───
async function loadExams(){
  const id = document.getElementById('exam-course-id').value;
  if(!id){toast('Enter Course ID','error');return}
  try {
    const data = await api('GET',`/api/exams/course/${id}`);
    const tbody = document.getElementById('exam-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="5"><div class="empty-state"><div class="empty-icon">📝</div><p>No exams</p></div></td></tr>';return}
    tbody.innerHTML = data.map(e=>`
      <tr>
        <td>${e.id}</td>
        <td><strong>${esc(e.examName)}</strong></td>
        <td>Course ${e.courseId}</td>
        <td>${e.examDate||'—'}</td>
        <td><span class="chip chip-yellow">${e.totalMarks} pts</span></td>
      </tr>
    `).join('');
  } catch(e){}
}
async function createExam(){
  const dto={examName:v('ex-name'),courseId:num('ex-cid'),totalMarks:parseFloat(document.getElementById('ex-marks').value),examDate:v('ex-date')};
  try { await api('POST','/api/exams',dto); toast('Exam created!','success'); closeModal('modal-exam') } catch(e){}
}
async function loadResults(){
  const id = document.getElementById('result-student-id').value;
  if(!id){toast('Enter Student ID','error');return}
  try {
    const data = await api('GET',`/api/exams/results/student/${id}`);
    const tbody = document.getElementById('result-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="3"><div class="empty-state"><div class="empty-icon">📊</div><p>No results</p></div></td></tr>';return}
    tbody.innerHTML = data.map(r=>`
      <tr>
        <td>Exam ${r.examId}</td>
        <td>${r.marksObtained}</td>
        <td><span class="chip ${gradeChip(r.grade)}">${r.grade||gradeCalc(r.marksObtained)}</span></td>
      </tr>
    `).join('');
  } catch(e){}
}
async function addResult(){
  const dto={examId:num('r-eid'),studentId:num('r-sid'),marksObtained:parseFloat(document.getElementById('r-marks').value)};
  try { await api('POST','/api/exams/results',dto); toast('Result added!','success'); closeModal('modal-result') } catch(e){}
}
function gradeCalc(marks){if(marks>=90)return'A+';if(marks>=80)return'A';if(marks>=70)return'B';if(marks>=60)return'C';if(marks>=50)return'D';return'F'}
function gradeChip(g){const m={'A+':'chip-green','A':'chip-green','B':'chip-blue','C':'chip-yellow','D':'chip-yellow','F':'chip-red'};return m[g]||'chip-gray'}

// ─── Library ───
async function loadBooks(){
  try { allBooks = await api('GET','/api/library/books'); renderBooks(allBooks) } catch(e){}
}
function renderBooks(data){
  const tbody = document.getElementById('book-tbody');
  if(!data?.length){tbody.innerHTML='<tr><td colspan="5"><div class="empty-state"><div class="empty-icon">📖</div><p>No books</p></div></td></tr>';return}
  tbody.innerHTML = data.map(b=>`
    <tr>
      <td><code style="background:var(--surface2);padding:2px 8px;border-radius:4px;font-size:.75rem">${esc(b.isbn)}</code></td>
      <td><strong>${esc(b.title)}</strong></td>
      <td>${esc(b.author)}</td>
      <td>${b.totalCopies}</td>
      <td>
        <button class="btn btn-secondary btn-sm" onclick="openIssueForBook(${b.id})">Issue</button>
        <button class="btn btn-danger btn-sm" onclick="deleteBook(${b.id})">🗑</button>
      </td>
    </tr>
  `).join('');
}
function filterBooks(){
  const q = document.getElementById('book-search').value.toLowerCase();
  renderBooks(allBooks.filter(b=>`${b.title} ${b.author} ${b.isbn}`.toLowerCase().includes(q)));
}
function openIssueForBook(id){document.getElementById('i-bid').value=id;openModal('modal-issue')}
async function addBook(){
  const dto={title:v('b-title'),author:v('b-author'),isbn:v('b-isbn'),totalCopies:num('b-copies')};
  try { await api('POST','/api/library/books',dto); toast('Book added!','success'); closeModal('modal-book'); loadBooks() } catch(e){}
}
async function deleteBook(id){
  if(!confirm('Delete this book?'))return;
  try { await api('DELETE',`/api/library/books/${id}`); toast('Book deleted','success'); loadBooks() } catch(e){}
}
async function loadIssues(){
  const id = document.getElementById('issue-student-id').value;
  if(!id){toast('Enter Student ID','error');return}
  try {
    const data = await api('GET',`/api/library/issues/student/${id}`);
    const tbody = document.getElementById('issue-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="6"><div class="empty-state"><div class="empty-icon">📚</div><p>No active issues</p></div></td></tr>';return}
    tbody.innerHTML = data.map(i=>`
      <tr>
        <td>${i.id}</td>
        <td>Book ${i.bookId}</td>
        <td>${i.issueDate||'—'}</td>
        <td>${i.expectedReturnDate||'—'}</td>
        <td><span class="chip ${i.status==='ISSUED'?'chip-yellow':'chip-green'}">${i.status||'ISSUED'}</span></td>
        <td><button class="btn btn-secondary btn-sm" onclick="returnBook(${i.id})">Return</button></td>
      </tr>
    `).join('');
  } catch(e){}
}
async function issueBook(){
  const dto={bookId:num('i-bid'),studentId:num('i-sid')};
  try { await api('POST','/api/library/issues/borrow',dto); toast('Book issued!','success'); closeModal('modal-issue') } catch(e){}
}
async function returnBook(id){
  try { await api('POST',`/api/library/issues/return/${id}`); toast('Book returned!','success'); loadIssues() } catch(e){}
}

// ─── Canteen ───
const foodEmojis=['🍕','🌮','🥗','🍔','🥪','🍜','🍲','🥙','🍱','🥘','🍛','🌯'];
async function loadMenu(){
  try {
    const data = await api('GET','/api/canteen/items/menu');
    const grid = document.getElementById('menu-grid');
    if(!data?.length){grid.innerHTML='<div class="empty-state"><div class="empty-icon">🍽️</div><p>Menu is empty</p></div>';return}
    grid.innerHTML = data.map((item,i)=>`
      <div class="menu-item ${item.isAvailable===false?'unavailable':''}">
        <div class="menu-emoji">${foodEmojis[i%foodEmojis.length]}</div>
        <h4>${esc(item.name)}</h4>
        <div class="price">EGP ${item.price?.toFixed(2)}</div>
        <div style="margin-top:8px"><span class="chip ${item.isAvailable!==false?'chip-green':'chip-red'}">${item.isAvailable!==false?'Available':'Unavailable'}</span></div>
      </div>
    `).join('');
  } catch(e){}
}
async function addMenuItem(){
  const dto={name:v('mi-name'),price:parseFloat(document.getElementById('mi-price').value),isAvailable:document.getElementById('mi-avail').value==='true'};
  try { await api('POST','/api/canteen/items',dto); toast('Item added!','success'); closeModal('modal-menu-item'); loadMenu() } catch(e){}
}
function addOrderItem(){
  orderItems.push({idx:orderItems.length});
  const list = document.getElementById('order-items-list');
  const row = document.createElement('div');
  row.style.cssText='display:flex;gap:8px;margin-bottom:8px;align-items:center';
  row.innerHTML=`<input type="number" placeholder="Item ID" class="oi-item" style="flex:1;background:var(--bg);border:1px solid var(--border);border-radius:6px;padding:8px;color:var(--text)"><input type="number" placeholder="Qty" class="oi-qty" style="width:70px;background:var(--bg);border:1px solid var(--border);border-radius:6px;padding:8px;color:var(--text)" value="1"><button onclick="this.parentElement.remove()" style="background:none;border:none;color:var(--danger);cursor:pointer;font-size:1.1rem">✕</button>`;
  list.appendChild(row);
}
async function placeOrder(){
  const rows = document.querySelectorAll('#order-items-list > div');
  const items = Array.from(rows).map(r=>({itemId:parseInt(r.querySelector('.oi-item').value),quantity:parseInt(r.querySelector('.oi-qty').value)})).filter(i=>i.itemId&&i.quantity);
  const dto={studentId:num('o-sid'),items};
  try { await api('POST','/api/canteen/orders',dto); toast('Order placed!','success'); closeModal('modal-order'); document.getElementById('order-items-list').innerHTML=''; } catch(e){}
}
async function loadOrders(){
  const id = document.getElementById('order-student-id').value;
  if(!id){toast('Enter Student ID','error');return}
  try {
    const data = await api('GET',`/api/canteen/orders/student/${id}`);
    const tbody = document.getElementById('order-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="4"><div class="empty-state"><div class="empty-icon">🛒</div><p>No orders</p></div></td></tr>';return}
    tbody.innerHTML = data.map(o=>`
      <tr>
        <td>${o.id}</td>
        <td>${o.orderDate?.split('T')[0]||'—'}</td>
        <td><strong>EGP ${o.totalPrice?.toFixed(2)||'—'}</strong></td>
        <td>${o.items?.length||0} item(s)</td>
      </tr>
    `).join('');
  } catch(e){}
}

// ─── Fees ───
async function loadFees(){
  const id = document.getElementById('fee-student-id').value;
  try {
    const data = await api('GET',`/api/payments/student/${id||''}`);
    renderFees(data);
  } catch(e){}
}
function renderFees(data){
  const tbody = document.getElementById('fee-tbody');
  if(!data?.length){tbody.innerHTML='<tr><td colspan="7"><div class="empty-state"><div class="empty-icon">💳</div><p>No payments</p></div></td></tr>';return}
  tbody.innerHTML = data.map(f=>`
    <tr>
      <td>${f.id}</td>
      <td>${f.studentId}</td>
      <td><strong>EGP ${f.amount?.toFixed(2)}</strong></td>
      <td>${esc(f.paymentType)}</td>
      <td>${f.paymentDate||'—'}</td>
      <td><code style="font-size:.75rem">${esc(f.transactionReference||'—')}</code></td>
      <td><span class="chip ${f.status==='PAID'?'chip-green':f.status==='PENDING'?'chip-yellow':'chip-red'}">${f.status||'—'}</span></td>
    </tr>
  `).join('');
}
async function recordPayment(){
  const dto={studentId:num('f-sid'),amount:parseFloat(document.getElementById('f-amount').value),paymentType:v('f-type'),transactionReference:v('f-ref')};
  try { await api('POST','/api/payments',dto); toast('Payment recorded!','success'); closeModal('modal-fee'); loadFees() } catch(e){}
}

// ─── Notices ───
async function loadNotices(){
  try {
    const data = await api('GET','/api/notices');
    const list = document.getElementById('notice-list-full');
    if(!data?.length){list.innerHTML='<div class="empty-state"><div class="empty-icon">📭</div><p>No notices</p></div>';return}
    list.innerHTML = data.map(n=>`
      <div class="notice-item" style="display:flex;justify-content:space-between;align-items:flex-start">
        <div style="flex:1">
          <h4>${esc(n.title)}</h4>
          <p style="margin:6px 0">${esc(n.content)}</p>
          <div class="notice-meta">📅 ${n.postedDate?.split('T')[0]||'—'} · by ${esc(n.postedByName||'Staff')}</div>
        </div>
        <button class="btn btn-danger btn-sm" onclick="deleteNotice(${n.id})" style="margin-left:12px;flex-shrink:0">🗑</button>
      </div>
    `).join('');
  } catch(e){}
}
async function postNotice(){
  const dto={title:v('n-title'),content:v('n-content'),postedById:num('n-tid')};
  try { await api('POST','/api/notices',dto); toast('Notice posted!','success'); closeModal('modal-notice'); loadNotices() } catch(e){}
}
async function deleteNotice(id){
  if(!confirm('Delete this notice?'))return;
  try { await api('DELETE',`/api/notices/${id}`); toast('Notice deleted','success'); loadNotices() } catch(e){}
}

// ─── Schedule ───
function buildScheduleGrid(){
  const days=['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'];
  const labels=['Mon','Tue','Wed','Thu','Fri','Sat','Sun'];
  const grid = document.getElementById('schedule-grid');
  grid.innerHTML = days.map((d,i)=>`
    <div class="schedule-day">
      <div class="schedule-day-label">${labels[i]}</div>
      <div id="sched-${d}"></div>
    </div>
  `).join('');
}
async function loadSchedule(){
  const id = document.getElementById('sched-course-id').value;
  if(!id){toast('Enter Course ID','error');return}
  buildScheduleGrid();
  try {
    const data = await api('GET',`/api/schedules/course/${id}`);
    data?.forEach(s=>{
      const col = document.getElementById(`sched-${s.dayOfWeek}`);
      if(col) col.innerHTML+=`<div class="schedule-slot"><div class="slot-course">Course ${s.courseId}</div><div class="slot-time">${s.startTime||''}–${s.endTime||''}</div><div style="color:var(--text3);font-size:.7rem">${s.roomNumber||''}</div></div>`;
    });
  } catch(e){}
}
async function addSchedule(){
  const dto={courseId:num('sc-cid'),teacherId:num('sc-tid'),dayOfWeek:v('sc-day'),startTime:v('sc-start'),endTime:v('sc-end'),roomNumber:v('sc-room')};
  try { await api('POST','/api/schedules',dto); toast('Schedule slot added!','success'); closeModal('modal-schedule'); loadSchedule() } catch(e){}
}

// ─── Enrollments ───
async function loadEnrollments(){
  const id = document.getElementById('enroll-student-id').value;
  if(!id){toast('Enter Student ID','error');return}
  try {
    const data = await api('GET',`/api/enrollments/student/${id}`);
    const tbody = document.getElementById('enroll-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="4"><div class="empty-state"><div class="empty-icon">📌</div><p>No enrollments</p></div></td></tr>';return}
    tbody.innerHTML = data.map(e=>`<tr><td>${e.id}</td><td>${e.studentId}</td><td>${e.courseId}</td><td>${e.enrollmentDate||'—'}</td></tr>`).join('');
  } catch(e){}
}
async function enrollStudent(){
  const dto={studentId:num('en-sid'),courseId:num('en-cid')};
  try { await api('POST','/api/enrollments',dto); toast('Student enrolled!','success'); closeModal('modal-enrollment') } catch(e){}
}

// ─── Users ───
async function loadUsers(){
  try {
    const data = await api('GET','/api/users');
    const tbody = document.getElementById('user-tbody');
    if(!data?.length){tbody.innerHTML='<tr><td colspan="5"><div class="empty-state"><div class="empty-icon">👤</div><p>No users</p></div></td></tr>';return}
    tbody.innerHTML = data.map(u=>`
      <tr>
        <td>${u.id}</td>
        <td><strong>${esc(u.username)}</strong></td>
        <td>${esc(u.email)}</td>
        <td><span class="chip ${u.role==='ADMIN'?'chip-yellow':u.role==='TEACHER'?'chip-blue':'chip-green'}">${u.role}</span></td>
        <td><button class="btn btn-danger btn-sm" onclick="deleteUser(${u.id})">🗑</button></td>
      </tr>
    `).join('');
  } catch(e){}
}
async function deleteUser(id){
  if(!confirm('Delete this user?'))return;
  try { await api('DELETE',`/api/users/${id}`); toast('User deleted','success'); loadUsers() } catch(e){}
}

// ─── Helpers ───
function v(id){return document.getElementById(id)?.value?.trim()||''}
function num(id){return parseInt(document.getElementById(id)?.value)||null}
function esc(s){if(!s)return'';return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;')}

// ─── Init ───
document.getElementById('a-date').value = new Date().toISOString().split('T')[0];
document.getElementById('ex-date').value = new Date().toISOString().split('T')[0];
buildScheduleGrid();

if(TOKEN){
  initApp();
} else {
  document.getElementById('auth-screen').style.display='flex';
}

// Enter key on login
document.getElementById('login-password').addEventListener('keydown',e=>{if(e.key==='Enter')doLogin()});
