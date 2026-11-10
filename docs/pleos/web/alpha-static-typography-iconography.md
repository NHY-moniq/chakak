> 자동 추출본 — 출처: https://document.pleos.ai/docs/connect/guide/docs-design/foundations/ colors·typography·iconography 이미지 판독
> 추출일: 2026-11-09. 값은 원본 그대로이며 앱 토큰으로 확정된 것이 아님. 요약은 ../design-foundations.md 참고.

# Pleos Connect 디자인 가이드 — Alpha / Static Color, Typography, Iconography

원본: `img/white-alpha.png`, `img/gray-alpha.png`, `img/light.png`, `img/dark.png`, `raster/*.svg.png`, `img/icon*.svg`
모든 토큰 이름 텍스트는 검정색으로 표기되어 있다 (주황 🟠 / 파랑 🔵 표시 없음) → "표시" 열 생략.

## Alpha Color

### White Alpha (`white-alpha.png`)

> 이미지 내 Light 열은 어두운 배경(#131417 계열) 위에, Dark 열은 흰 배경 위에 스와치를 표시함. 표기된 값은 아래와 같이 그대로 옮김.

| Light | Dark | Token Name |
|---|---|---|
| #131417, 5% | #FFFFFF, 5% | basic_alpha_light_50 |
| #131417, 10% | #FFFFFF, 10% | basic_alpha_light_100 |
| #131417, 20% | #FFFFFF, 20% | basic_alpha_light_200 |
| #131417, 30% | #FFFFFF, 30% | basic_alpha_light_300 |
| #131417, 40% | #FFFFFF, 40% | basic_alpha_light_400 |
| #131417, 50% | #FFFFFF, 50% | basic_alpha_light_500 |

### Gray Alpha (`gray-alpha.png`)

| Light | Dark | Token Name |
|---|---|---|
| #FFFFFF, 5% | #131417, 5% | basic_alpha_dark_50 |
| #FFFFFF, 10% | #131417, 10% | basic_alpha_dark_100 |
| #FFFFFF, 20% | #131417, 20% | basic_alpha_dark_200 |
| #FFFFFF, 30% | #131417, 30% | basic_alpha_dark_300 |
| #FFFFFF, 40% | #131417, 40% | basic_alpha_dark_400 |
| #FFFFFF, 50% | #131417, 50% | basic_alpha_dark_500 |

> 주의: 원본 표기 그대로 옮김. "White Alpha" 파일의 Light 열 값이 #131417(어두운 색)이고 "Gray Alpha" 파일의 Light 열 값이 #FFFFFF이다 — 파일명/섹션명과 값이 직관과 반대로 보이지만 이미지에 적힌 값 그대로이다. 스와치 렌더링도 이 표기와 일치함(white-alpha 이미지의 Light 열 배경이 어두움).

## Static Color

모드와 무관한 고정 색(열 이름 "Common").

### Light (`light.png`)

| Common | Color Token |
|---|---|
| #FFFFFF | basic_static_light_100 |
| #FFFFFF, 84% | basic_static_light_200 |
| #FFFFFF, 64% | basic_static_light_300 |
| #FFFFFF, 32% | basic_static_light_400 |

### Dark (`dark.png`)

| Common | Color Token |
|---|---|
| #131417 | basic_static_dark_100 |
| #131417, 84% | basic_static_dark_200 |
| #131417, 64% | basic_static_dark_300 |
| #131417, 32% | basic_static_dark_400 |

## Typography

> 단위는 이미지에 표기되어 있지 않다 (px/sp/dp 미기재). Line height 열은 없음 — 표는 Scale Category / Weight / Size 3열만 있다.

### Headline (`headline.svg.png`)

| Scale Category | Weight | Size |
|---|---|---|
| Large | Extra Bold, Bold | 60 |
| Medium | Extra Bold, Bold | 56 |
| Small | Extra Bold, Bold | 48 |

### Title (`title.svg.png`)

| Scale Category | Weight | Size |
|---|---|---|
| Large | Extra Bold, Bold, Regular | 40 |
| Medium | Extra Bold, Bold, Regular | 36 |
| Small | Extra Bold, Bold, Regular | 32 |

### Body (`body.svg.png`)

| Scale Category | Weight | Size |
|---|---|---|
| Large | Strong, Normal | 30 |
| Medium | Strong, Normal | 28 |
| Small | Strong, Normal | 26 |

> Body의 Weight 명칭은 "Extra Bold/Bold"가 아니라 "Strong, Normal"로 표기됨.

### Label (`label.svg.png`)

| Scale Category | Weight | Size |
|---|---|---|
| Medium | Normal | 24 |
| Small | Normal | 20 |

> Label에는 Large 행이 없다 (원본 SVG 높이 150 — 잘림이 아니라 2행이 전부). Label의 Medium/Small 견본 글자는 Regular 굵기로 렌더링됨.

## Iconography

> 래스터 주의: `raster/icon1.svg.png`는 headline 이미지와 동일하지 않음(내용이 아이콘 다이어그램임) — 다만 `raster/icon*.svg.png` 3장 모두 오른쪽이 잘려 있었다(원본 SVG 1000×379/400을 정사각 렌더 시 확대·크롭). SVG를 정사각 viewBox로 감싸 `crop/sq/icon{1,2,3}.svg.png`로 다시 렌더링해 전체를 확인함. 다이어그램 안에 숫자 표기는 icon2의 "1.5px", "3.6px"뿐이며, 그리드 치수는 이미지에 인쇄되어 있지 않아 **SVG 지오메트리에서 도출**했다(아래 [SVG 도출] 표시).

### 1) Grid / Creation (`icon1.svg`)

이미지 구성: 왼쪽 "Grid" 라벨이 붙은 빈 키라인 그리드 1개 → 화살표 → "Creation" 라벨 아래 그리드 위에 그린 예시 아이콘 5개 (원형 느낌표/경고, 삼각형 느낌표/주의, 둥근 사각형 화면, 비디오 카메라, 이미지(산+해)). 키라인은 마젠타(#FF00FF), 예시 아이콘은 회색(#131417, opacity 0.2) 채움.

[SVG 도출] 각 아트보드 및 키라인 (아트보드 좌상단 기준 좌표, SVG 단위):

| 요소 | 크기 | 위치/여백 |
|---|---|---|
| 아트보드(Grid 프레임) | 96 × 96 | — |
| 정사각 키라인 | 72 × 72 | 사방 12 여백 (x 12–84, y 12–84) |
| 세로 직사각 키라인 | 64 × 80 | 좌우 16, 상하 8 여백 |
| 가로 직사각 키라인 | 80 × 64 | 좌우 8, 상하 16 여백 |
| 원 키라인 | 지름 84 (r 42) | 중심 (48, 48), 사방 6 여백 |
| 삼각 키라인 | 밑변 96, 높이 88 | 꼭짓점 (48, 0), 밑변 y = 88 (아래 8 여백) |
| 키라인 선 두께 | 0.2 | 표시용 가이드선 |

- 첫 번째 "Grid" 프레임의 삼각형만 미세하게 다름: 꼭짓점 (47.99, 0.21), 밑변 x 0.17–95.83, y 87.9 (사실상 동일, 렌더 오차 수준).
- 예시: 원형 경고 아이콘은 원 키라인(지름 84)을 꽉 채움, 삼각 경고 아이콘은 삼각 키라인에 맞춤(밑변 y 88), 화면 아이콘 외곽은 80 × 60 (x 8–88, y 18–78) 둥근 사각형(모서리 r 12), 내부 사각형 60 × 40 (x 18–78, y 28–68, r 4).
- 단위는 이미지에 표기되지 않음. 96 아트보드 기준 그리드로 해석 가능하나, 실제 px 단위인지는 확인 불가.
- "Live area"라는 용어나 패딩 수치 라벨은 이미지에 없음.

### 2) Style: Filled / Outlined, Stroke (`icon2.svg`)

왼쪽 패널:
- 시계 아이콘 2종 비교 — 라벨 **"Filled"** (검정 원 채움 + 흰 시곗바늘), **"Outlined"** (검정 원 테두리 + 검정 시곗바늘). 각 아이콘 프레임(흰 박스) 약 160 × 160 SVG 단위 [SVG 도출].

오른쪽 패널 (확대 상세 2개):
- 왼쪽 박스: 회색(#9FA7B7, opacity 0.6 [SVG 도출]) 사각형 모서리에 점선 원 — 모서리 라운드 표시, 라벨 **"기본 1.5px"** (모서리 반경 기본값 1.5px로 해석; 이미지엔 "기본 1.5px"만 표기).
- 오른쪽 박스: 세로 선(스트로크) 끝에 점선 반원 + 중심 점선 — 라벨 **"Round join, Round cap"**, **"기본 3.6px"** (스트로크 두께 기본값 3.6px로 해석).
- 점선 원의 stroke-width 0.788043, 색 #686A72 [SVG 도출, 표시용]. 라벨 글자색 #696B74.
- 1.5px / 3.6px가 어느 아이콘 크기 기준인지는 이미지에 표기 없음.

### 3) 아이콘 카테고리 (`icon3.svg`)

크기 변형(size variant) 표는 **없음** — 이 이미지는 아이콘 종류 4가지를 보여준다. 숫자 표기 없음.

| 라벨 | 예시 아이콘 | 색 |
|---|---|---|
| Vehicle Icon | 스티어링 휠 | #131417 [SVG 도출] |
| System Icon | 전화 수화기 | #131417 [SVG 도출] |
| Climate Icon | 시트 히터(좌석 + 열선 3줄) | 좌석 #131417, 열선 2줄 #FF2E00, 1줄 #131417 fill-opacity 0.32 (단계 표시) [SVG 도출] |
| Telltale Icon | 오토 하이빔(헤드램프 + "A") | #0064FF [SVG 도출] |

- [SVG 도출] 각 카드: 약 160 × 160, 모서리 r 24, 흰색, 배경 패널 #F2F2F2 (r 20).
