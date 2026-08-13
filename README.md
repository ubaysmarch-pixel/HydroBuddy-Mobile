# HydroBuddy Mobile v1

Versi awal kalkulator nutrisi hidroponik Android yang terinspirasi dari konsep HydroBuddy.

## Fitur v1
- Input volume tandon.
- Target ppm N, P, K, Ca, Mg, S, Fe, Mn, Zn, Cu, B, Mo.
- Database awal pupuk.
- Input gram masing-masing pupuk.
- Perhitungan kontribusi ppm unsur.
- Perbandingan hasil terhadap target.
- Build APK otomatis melalui GitHub Actions.
- Offline setelah aplikasi terpasang.

## Catatan lisensi
HydroBuddy asli adalah proyek open-source. Proyek ini adalah implementasi mobile baru dan tidak mengklaim sebagai aplikasi resmi HydroBuddy. Sebelum menyalin source code asli atau mendistribusikan turunan langsung, periksa LICENSE dan kewajiban GPL dari proyek sumber.

## Build
Workflow GitHub Actions ada di `.github/workflows/build.yml`.

Jalankan workflow `Build HydroBuddy Mobile APK`, lalu buka artifact:
`HydroBuddy-Mobile-v1-APK`.

## Rumus dasar
ppm unsur = gram pupuk × (% unsur / 100) × 1,000,000 / volume(L) / 1000

yang setara dengan:

ppm = gram × persen × 10,000 / volume(L) / 100
