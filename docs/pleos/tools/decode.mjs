import fs from 'fs'; import * as kiwi from 'kiwi-schema'; import pako from 'pako'; import * as fzstd from 'fzstd';
const [,, inp, out] = process.argv;
const buf = new Uint8Array(fs.readFileSync(inp));
const dv = new DataView(buf.buffer);
let off = 12; const chunks = [];
while (off < buf.length) { const len = dv.getUint32(off, true); off += 4; chunks.push(buf.slice(off, off+len)); off += len; }
const inflate = c => (c[0]===0x28 && c[1]===0xb5) ? fzstd.decompress(c) : pako.inflateRaw(c);
const schema = kiwi.compileSchema(kiwi.decodeBinarySchema(inflate(chunks[0])));
const msg = schema.decodeMessage(inflate(chunks[1]));
fs.writeFileSync(out, JSON.stringify(msg, (k,v)=> (v instanceof Uint8Array) ? `<bytes ${v.length}>` : v));
console.log(inp, 'chunks', chunks.length, 'nodes', msg.nodeChanges?.length);
