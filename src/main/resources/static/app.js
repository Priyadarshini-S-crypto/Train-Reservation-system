const API = '/api/train';

// small helper
function toast(targetId, text, timeout=3000){
  const el = document.getElementById(targetId);
  if(!el) return alert(text);
  el.textContent = text;
  setTimeout(()=>{ if(el.textContent===text) el.textContent=''; }, timeout);
}

async function refreshAll(){
  await Promise.all([loadAvailable(), loadBooked()]);
}

async function loadAvailable(){
  try{
    const r = await fetch(API + '/available');
    const data = await r.json();
    const row = document.getElementById('availRow');
    row.innerHTML = '';
    for(const k in data){
      const c = document.createElement('div');
      c.className = 'statusCard';
      c.innerHTML = `<strong>${k}</strong><div style="font-size:20px;margin-top:6px">${data[k]}</div>`;
      row.appendChild(c);
    }
  }catch(e){
    console.error(e);
  }
}

async function loadBooked(){
  try{
    const r = await fetch(API + '/booked');
    const data = await r.json();
    const target = document.getElementById('bookedList');
    target.innerHTML = '';
    if(!Array.isArray(data) || data.length===0) target.innerText='No Passenger details';
    else data.forEach(p=>{
      const d = document.createElement('div');
      d.style.padding='8px';
      d.style.borderBottom='1px solid #eef6ff';
      d.innerText = `ID:${p.passengerId} • ${p.name} • ${p.age} yrs • ${p.allocated} ${p.number}`;
      target.appendChild(d);
    });
  }catch(e){
    console.error(e);
    document.getElementById('bookedList').innerText='Failed to load';
  }
}

document.getElementById('bookBtn').addEventListener('click', async ()=>{
  const name = document.getElementById('name').value.trim() || 'Unknown';
  const age = Number(document.getElementById('age').value) || 0;
  const berth = document.getElementById('berth').value;
  try{
    const res = await fetch(API + '/book', {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify({name, age, berthPreference: berth})
    });
    const result = await res.json();
    if(res.ok){
      toast('bookInfo', result.message || 'Booked');
      refreshAll();
      document.getElementById('name').value=''; document.getElementById('age').value='';
    }else{
      toast('bookInfo', (result.message || 'Booking failed'));
    }
  }catch(e){
    toast('bookInfo', 'Network error');
  }
});

document.getElementById('cancelBtn').addEventListener('click', async ()=>{
  const id = Number(document.getElementById('cancelId').value);
  if(!id){ toast('cancelInfo','Enter valid id'); return; }
  try{
    const res = await fetch(API + '/cancel/' + id, {method:'DELETE'});
    const r = await res.json();
    if(res.ok) toast('cancelInfo', r.message || 'Cancelled');
    else toast('cancelInfo', r.message || 'Cancel failed');
    refreshAll();
  }catch(e){
    toast('cancelInfo','Network error');
  }
});

document.getElementById('refreshBtn').addEventListener('click', refreshAll);
document.getElementById('searchBtn').addEventListener('click', ()=>{
  const id = Number(document.getElementById('searchId').value);
  if(!id) return alert('Enter id');
  // search in current list
  const items = document.querySelectorAll('#bookedList > div');
  let found=false;
  items.forEach(it=>{
    if(it.innerText.includes(`ID:${id} `)){ found=true; it.style.background='#fffbeb'; setTimeout(()=>it.style.background='transparent',2000); }
  });
  if(!found) alert('No passenger with that id in current list (try Refresh).');
});

document.getElementById('quickL').addEventListener('click', ()=>{
  document.getElementById('berth').value='L';
  document.getElementById('bookBtn').click();
});

// initial load
refreshAll();
