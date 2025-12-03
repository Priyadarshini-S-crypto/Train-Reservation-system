
const apiRoot = '/api/train';

async function refreshAvailable(){
  const r = await fetch(apiRoot + '/available');
  const data = await r.json();
  document.getElementById('avail').innerText = JSON.stringify(data, null, 2);
}

async function refreshBooked(){
  const r = await fetch(apiRoot + '/booked');
  const data = await r.json();
  const target = document.getElementById('booked');
  target.innerHTML = '';
  if(Array.isArray(data) && data.length===0){ target.innerText = 'No Passenger details'; return; }
  data.forEach(p=>{
    const d = document.createElement('div');
    d.innerText = `ID:${p.passengerId} Name:${p.name} Age:${p.age} Status:${p.allocated} ${p.number}`;
    target.appendChild(d);
  });
}

document.getElementById('bookBtn').addEventListener('click', async ()=>{
  const name = document.getElementById('name').value || 'Unknown';
  const age = Number(document.getElementById('age').value) || 0;
  const berth = document.getElementById('berth').value;
  const res = await fetch(apiRoot + '/book', {
    method:'POST', headers:{'Content-Type':'application/json'},
    body: JSON.stringify({name, age, berthPreference: berth})
  });
  const data = await res.json();
  alert(data.message || JSON.stringify(data));
  refreshAvailable(); refreshBooked();
  document.getElementById('name').value=''; document.getElementById('age').value='';
});

document.getElementById('cancelBtn').addEventListener('click', async ()=>{
  const id = Number(document.getElementById('cancelId').value);
  if(!id){ alert('Enter valid id'); return; }
  const res = await fetch(apiRoot + '/cancel/' + id, { method:'DELETE' });
  const data = await res.json();
  alert(data.message || JSON.stringify(data));
  refreshAvailable(); refreshBooked();
});

document.getElementById('loadAvail').addEventListener('click', refreshAvailable);
document.getElementById('loadBooked').addEventListener('click', refreshBooked);

refreshAvailable(); refreshBooked();
