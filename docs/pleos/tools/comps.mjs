import fs from 'fs';
const [,, inp, out] = process.argv;
const N = JSON.parse(fs.readFileSync(inp)).nodeChanges;
const gid = g => g ? `${g.sessionID}:${g.localID}` : '';
const byId = new Map(N.map(n => [gid(n.guid), n])); const varByKey = new Map(N.filter(n=>n.type==='VARIABLE').map(v=>[v.key,v]));
const kids = new Map(); N.forEach(n => { const p = gid(n.parentIndex?.guid); if(!kids.has(p)) kids.set(p, []); kids.get(p).push(n); });
const hex = c => '#' + [c.r,c.g,c.b].map(x => Math.round(x*255).toString(16).padStart(2,'0')).join('').toUpperCase() + (c.a<1?`/${Math.round(c.a*100)}%`:'');
const vname = p => { const a = p?.colorVar?.value?.alias; const v = a && (varByKey.get(a.assetRef?.key) || byId.get(gid(a.guid))); return v ? v.name : null; };
const paint = ps => (ps||[]).filter(p=>p.visible!==false).map(p => vname(p) || (p.color ? hex(p.color) : p.type)).join('+');
const desc = n => { const s = n.size ? `${Math.round(n.size.x)}×${Math.round(n.size.y)}` : '';
  const r = n.cornerRadius ? ` r${n.cornerRadius}` : (n.rectangleTopLeftCornerRadius ? ` r${n.rectangleTopLeftCornerRadius}` : '');
  const pad = (n.stackHorizontalPadding||n.stackVerticalPadding||n.stackPaddingRight||n.stackPaddingBottom) ? ` pad[h${n.stackHorizontalPadding||0} v${n.stackVerticalPadding||0} r${n.stackPaddingRight??''} b${n.stackPaddingBottom??''}]` : '';
  const gap = n.stackSpacing ? ` gap${n.stackSpacing}` : ''; const f = paint(n.fillPaints); const st = (n.strokePaints||[]).length && n.strokeWeight ? ` stroke${n.strokeWeight}:${paint(n.strokePaints)}` : '';
  const t = n.type==='TEXT' ? ` "${(n.textData?.characters||'').slice(0,30)}" ${n.fontSize||''}/${n.fontName?.style||''}` : '';
  return `${n.type} ${n.name} ${s}${r}${pad}${gap}${f?' fill:'+f:''}${st}${t}`; };
const page = N.find(n => n.type==='CANVAS' && n.name.includes('Components'));
let md = '';
function walk(n, d, maxD) { md += '  '.repeat(d) + '- ' + desc(n) + '\n'; if (d < maxD) (kids.get(gid(n.guid))||[]).forEach(c => walk(c, d+1, maxD)); }
for (const sec of kids.get(gid(page.guid))||[]) { if (sec.type==='TEXT') continue; md += `\n## ${sec.name}\n`;
  // list component sets / symbols with their direct children (depth 2)
  const syms = []; const find = n => { if (n.type==='SYMBOL') syms.push(n); else (kids.get(gid(n.guid))||[]).forEach(find); }; find(sec);
  const seen = new Set(); syms.forEach(s => { const key = s.name; if (seen.has(key)) return; seen.add(key); walk(s, 0, 2); }); }
fs.writeFileSync(out, md); console.log(md.split('\n').length, 'lines');
