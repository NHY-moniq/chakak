import fs from 'fs';
const [,, inp, out, title] = process.argv;
const N = JSON.parse(fs.readFileSync(inp)).nodeChanges;
const gid = g => `${g.sessionID}:${g.localID}`;
const vars = N.filter(n => n.type === 'VARIABLE'), sets = N.filter(n => n.type === 'VARIABLE_SET');
const varByKey = new Map(vars.map(v => [v.key, v])), setByKey = new Map(sets.map(s => [s.key, s]));
const modeName = new Map(); sets.forEach(s => (s.variableSetModes||[]).forEach(m => modeName.set(gid(m.id), m.name)));
const hex = c => '#' + [c.r,c.g,c.b].map(x => Math.round(x*255).toString(16).padStart(2,'0')).join('').toUpperCase() + (c.a < 1 ? ` α${Math.round(c.a*100)}%` : '');
const setOf = v => setByKey.get(v.variableSetID?.assetRef?.key) || N.find(s => s.type==='VARIABLE_SET' && v.variableSetID?.guid && gid(s.guid)===gid(v.variableSetID.guid));
function resolve(v, wantMode, depth=0) {
  const es = v.variableDataValues?.entries || []; if (!es.length || depth > 8) return {txt:'?'};
  const e = es.find(e => modeName.get(gid(e.modeID)) === wantMode) || es[0];
  const d = e.variableData; const val = d.value || {};
  if (d.dataType === 'ALIAS') { const ref = val.alias?.assetRef?.key; const t = varByKey.get(ref) || (val.alias?.guid && vars.find(x=>gid(x.guid)===gid(val.alias.guid)));
    if (!t) return {txt:'(alias 미해결)'}; const r = resolve(t, wantMode, depth+1); return {txt: r.txt, via: t.name}; }
  if (val.colorValue) return {txt: hex(val.colorValue)};
  if (val.floatValue !== undefined) return {txt: String(+val.floatValue.toFixed(3))};
  if (val.textValue !== undefined) return {txt: JSON.stringify(val.textValue)};
  if (val.boolValue !== undefined) return {txt: String(val.boolValue)};
  return {txt: JSON.stringify(val).slice(0,60)};
}
let md = `# ${title}\n\n## Variables\n`;
const grouped = new Map(); vars.forEach(v => { const s = setOf(v); const k = s ? s.name + ' ⟨' + s.key?.slice(0,6) + '⟩' : '(no set)'; if(!grouped.has(k)) grouped.set(k, {s, vs: []}); grouped.get(k).vs.push(v); });
for (const [k, {s, vs}] of grouped) {
  const modes = (s?.variableSetModes || []).map(m => m.name); if (!modes.length) modes.push('default');
  md += `\n### ${k} — ${vs.length}개, modes: ${modes.join(' / ')}\n\n| name | type | ${modes.join(' | ')} |\n|---|---|${modes.map(()=> '---|').join('')}\n`;
  const seen = new Set();
  vs.sort((a,b)=>a.name.localeCompare(b.name)).forEach(v => { if (seen.has(v.name)) return; seen.add(v.name);
    md += `| ${v.name} | ${v.variableResolvedType||''} | ${modes.map(m => { const r = resolve(v, m); return r.via ? `${r.txt} ← ${r.via}` : r.txt; }).join(' | ')} |\n`; });
}
md += `\n## Text styles\n\n| name | family | style | size | lineHeight | letterSpacing |\n|---|---|---|---|---|---|\n`;
const seenT = new Set();
N.filter(n => n.styleType === 'TEXT').sort((a,b)=>a.name.localeCompare(b.name)).forEach(t => { if (seenT.has(t.name)) return; seenT.add(t.name);
  md += `| ${t.name} | ${t.fontName?.family} | ${t.fontName?.style} | ${t.fontSize} | ${t.lineHeight ? t.lineHeight.value + ' ' + t.lineHeight.units : ''} | ${t.letterSpacing ? t.letterSpacing.value + ' ' + t.letterSpacing.units : ''} |\n`; });
md += `\n## Effect styles\n\n`;
N.filter(n => n.styleType === 'EFFECT').forEach(e => { md += `- **${e.name}**: ` + (e.effects||[]).map(x => `${x.type} x${x.offset?.x} y${x.offset?.y} blur${x.radius} spread${x.spread||0} ${x.color ? hex(x.color) : ''}`).join('; ') + '\n'; });
md += `\n## Fill styles\n\n`;
N.filter(n => n.styleType === 'FILL').forEach(f => { md += `- **${f.name}**: ` + (f.fillPaints||[]).map(p => p.color ? hex(p.color) : p.type).join(', ') + '\n'; });
fs.writeFileSync(out, md); console.log(out, md.split('\n').length, 'lines');
