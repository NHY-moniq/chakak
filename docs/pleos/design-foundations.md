# Pleos Connect 디자인 기초 정리 (차칵 적용용)

확인일: 2026-11-09. 차칵 Compose 디자인 토큰의 근거 문서다. 각 값의 출처를 표시했다.

- **[Figma]** `docs/Pleos_Connect_design_assets_215.fig` 원본 데이터(canvas.fig 디코딩). Light/Dark 값과 alias까지 해석함. **가장 신뢰도 높음.**
- **[Display]** `docs/Pleos_Connect_display_guide_215.fig`
- **[Web]** https://document.pleos.ai/docs/connect/guide/docs-design/ 페이지 본문과 표 이미지 판독

원본 수준의 상세 자료:

| 파일 | 내용 |
| --- | --- |
| `figma/design-assets-tokens.md` | Figma 변수 722개(색상·폼팩터별 치수·타이포), 텍스트·그림자 스타일 |
| `figma/design-assets-components.md` | Buttons·Fields·Tabs·Page Navigation·Popup 등 256개 컴포넌트 변형의 크기·패딩·반경·색 토큰 |
| `figma/display-guide-tokens.md` | 디스플레이 가이드 변수 1,009개(브랜드 테마·폼팩터) |
| `figma/display-guide-layout.md` | 폼팩터·화면 분할 설명 노드 트리 |
| `web/colors-system.md` | 웹 System Color 20개 그룹 표(118행) |
| `web/alpha-static-typography-iconography.md` | 웹 Alpha·Static 색상, 타입 스케일, 아이콘 그리드 |
| `tools/*.mjs` | `.fig` 재추출 스크립트(`kiwi-schema`, `pako`, `fzstd` 필요) |

---

## 1. 원칙 [Web Introduction]

- 1~2초 안에 파악할 수 있는 핵심 정보만 노출한다. 입력 후 0.25초 안에 응답하고, 지연되면 처리 중 상태를 표시한다.
- 동영상·애니메이션·자동 스크롤처럼 시선을 끄는 요소를 금지한다. 한 손으로 단순하게 조작할 수 있어야 한다.
- 아이콘·텍스트·이미지 대비는 4.5:1 이상이다. 중요 정보는 높이 5 mm 이상, 보조 정보는 3 mm 이상이다.
- 한 줄은 80자 이하, 문단은 3줄 이하다. 아이콘에는 텍스트 라벨을 함께 쓴다. 터치하면 햅틱 또는 음성 피드백을 준다.
- 주간/야간 모드를 지원한다. Gleo 음성 대화는 수동으로 시작·종료·중단할 수 있어야 한다.
- 최소 폰트는 12sp, 실제 픽셀 높이는 18px 이상이다. [Web Typography]

## 2. 색상

### 2.1 Grayscale `basic_*` [Web 판독 = Figma 일치]

| 토큰 | Light | Dark |
| --- | --- | --- |
| basic_00 | #FFFFFF | #131417 |
| basic_50 | #F7F8FA | #1D1E21 |
| basic_100 | #EDEEF2 | #313236 |
| basic_200 | #E0E1E6 | #44464E |
| basic_300 | #C7C9CE | #686A72 |
| basic_400 | #A4A8AE | #A4A8AE |
| basic_500 | #686A72 | #C7C9CE |
| basic_600 | #44464E | #E0E1E6 |
| basic_700 | #313236 | #EDEEF2 |
| basic_800 | #1D1E21 | #F7F8FA |
| basic_900 | #131417 | #FFFFFF |

### 2.2 차칵에 쓸 의미 토큰 [Figma]

| 토큰 | Light | Dark | 차칵 용도 |
| --- | --- | --- | --- |
| background/primary | #F7F8FA | #1D1E21 | 앱 배경 |
| background/secondary | #FFFFFF | #131417 | 보조 배경 |
| background/popup | #FFFFFF | #313236 | 삭제 확인 팝업 |
| surface/basic | #FFFFFF | #1D1E21 | 기록 카드, Page Navigation |
| surface/high | #EDEEF2 | #313236 | 강조 패널(상세 요약) |
| surface/accent | #E0E1E6 | #44464E | 선택된 행 |
| text/dark/primary | #131417 | #FFFFFF | 본문·제목 |
| text/dark/secondary | #131417 84% | #FFFFFF 84% | 보조 본문 |
| text/dark/tertiary | #131417 64% | #FFFFFF 64% | 시각·메타 정보 |
| text/dark/quaternary | #131417 32% | #FFFFFF 32% | 비활성 라벨에만 사용 (대비 부족, 아래 참고) |
| text/light/primary | #FFFFFF | #131417 | filled 버튼 위 글자 |
| icon/dark/primary~quaternary | text/dark와 동일 | | 아이콘 |
| divider/primary·secondary·tertiary | #131417 5·10·20% | #FFFFFF 5·10·20% | 구분선, 버튼·필드 테두리 |
| button/basic/enabled·pressed | #131417 5%·10% | #FFFFFF 5%·10% | 보조 버튼 |
| button/basic/disabled | #FFFFFF 20% | #131417 20% | 테두리 divider/secondary와 함께 |
| button/filled/enabled·pressed | #44464E·#313236 | #E0E1E6·#C7C9CE | 주요 버튼("지금 기록", "저장") |
| button/filled/disabled | #131417 5% | #FFFFFF 5% | |
| regulation/red | #F62E24 | #FF4339 | 오류·삭제 |
| regulation/blue, informative/positive | #0064FF | #0082FF | 강조·선택·링크성 정보 |
| informative/active | #02C265 | #00E074 | 완료 상태 |

Pleos 의미 토큰에는 **별도의 error·warning·success 토큰이 없다.** 위의 regulation·informative 색을 차칵 상태 표현에 빌려 쓰는 것은 차칵의 선택이다. 나머지 그룹(Controller·Dropdown·Switch·Dim·Gleo AI·Keyboard 등)은 상세 파일에 있다.

### 2.3 대비 검증 (WCAG, 차칵 계산)

| 전경 | 배경 | Light | Dark | 판정 |
| --- | --- | --- | --- | --- |
| text primary | background/primary | 17.3 | 16.7 | 통과 |
| text secondary | background/primary | 11.0 | 12.0 | 통과 |
| text tertiary | background/primary | 5.4 | 7.5 | 통과 |
| text quaternary | background/primary | **2.1** | **2.9** | 4.5 미달: 의미 있는 정보에 사용 금지 |
| regulation/red | background/primary | **3.7** | 4.9 | Light에서 본문 크기 텍스트로 사용 금지. 아이콘·큰 글자, 또는 text primary와 함께 사용 |
| informative/positive | background/primary | 4.6 | **4.5** | 경계값 |
| informative/active | background/primary | **2.2** | 9.5 | Light에서 텍스트 사용 금지. 아이콘 배경·점 표시만 사용 |

→ 상태(완료·부분·오류·미지원)는 **색 + 아이콘 + 텍스트**로 표현하고, 텍스트는 text primary·secondary로 쓴다.

## 3. 타이포그래피 [Figma 텍스트 스타일 = Web 타입 스케일 일치]

폰트는 **Asta Sans**(Google Fonts, 한글·영문)다. 아래 수치는 Figma px이며 connect-s 기준이다.

| 스타일 | 굵기 | 크기 / 줄높이 |
| --- | --- | --- |
| Headline Large | ExtraBold, Bold | 60 / 80 |
| Headline Medium | ExtraBold | 56 / 72 |
| Headline Small | ExtraBold, Bold | 48 / 64 |
| Title Large | ExtraBold, Bold, Regular | 40 / 52 |
| Title Medium | ExtraBold, Bold, Regular | 36 / 44 |
| Title Small | Bold | 32 / 40 |
| Body Large | Bold, Regular | 30 / 38 |
| Body Medium | Bold | 28 / 36 |
| Body Small | Bold, Regular | 26 / 34 |
| Label Medium | Regular | 24 / 28 |
| Label Small | Regular | 20 / 24 |

Body는 웹에서 "Strong / Normal"로 표기한다. letterSpacing은 0이다.

**폼팩터별 스케일** [Figma `typo` 변수]: 같은 레벨이 디스플레이마다 다르다. 예를 들어 size level 15는 connect-s 48 / connect-l 42 / connect-w 40 / waterfall 51이다. connect-l은 약 0.875배, connect-w는 약 0.83배, waterfall은 약 1.06배다.

Figma 내부 캔버스의 `42dot Sans`·`Manrope` 스타일은 문서 프레임용으로 보이며 앱 UI에는 쓰지 않는다.

## 4. 형태·간격·그림자 [Figma]

- **반경**(connect-s): 버튼 Large 32 / Medium 24 / Small 16, Tabs 32, Dropdown 32, Popup 48(Extended 40), 알림 카드 48. `radius level4` = 12, `level5` = 16.
- **그림자**
  - Level1: y6 blur24, 검정 8%
  - Level2: y8 blur32 16% + y2 blur2 8%
  - Level3: y20 blur40 24% + y2 blur4 20%
- **선 두께**: line level1 = 1, level4 = 4. 버튼·필드 테두리는 2.

## 5. 컴포넌트 치수 [Figma, connect-s px]

| 컴포넌트 | 변형 | 크기 | 패딩 / 간격 | 반경 | 색 |
| --- | --- | --- | --- | --- | --- |
| Button Large | Primary/Secondary × Enabled·Pressed·Disabled | 높이 100 | h40 v16, gap16, 아이콘 52, 라벨 32 Bold | 32 | Primary = button/basic, Secondary = button/filled |
| Button Medium | | 높이 80 | h32 v16, gap14, 아이콘 40, 라벨 30 Bold | 24 | |
| Button Small | | 높이 64 | h24 v8, gap12, 아이콘 32, 라벨 26 Bold | 16 | |
| Tabs (Segmented) | 3~6 세그먼트 | 높이 100 (3개 880폭, 4~6개 1176폭) | 내부 8 | 32 | controller/tab_background |
| Text Field | Small / Medium × Enabled·Focused·… | 700×80 / 700×100 | gap 12 / 16 | | |
| Dropdown | Small / Medium | 600×80 / 600×100 | h20 | 32 | dropdown/normal·pressed, 테두리 divider/secondary 2 |
| Page Navigation | Radio·Phone·+Segmented | 높이 144 (탭 포함 232) | v40 b24 | | surface/basic, 하단선 divider/tertiary 1 |
| Popup | Medium / Large | 808×366 / 1200×366 | 48, gap40 | 48 | background/popup, 테두리 divider/primary 2 |
| Selection (체크·라디오) | Active × State | 64×64 | | | |
| System Notification | 텍스트 / 이미지+텍스트 | 700×270~276 | | 48 | |

Toast 전용 컴포넌트는 이 Figma 파일에 없다. System Notification이 가장 가깝다.

## 6. 아이콘 [Web Iconography + 판독]

- 기본 48×48dp 그리드로 제작하고, 1벌을 만든 뒤 크기만 조정한다.
- 스타일은 Filled / Outlined 두 가지다. stroke 기본값은 3.6px(Round join·cap), 모서리는 1.5px이다. 둘 다 그리드 크기 기준이 명시되지 않았다.
- SVG 기하로 추정한 값: 96 아트보드 기준 사각 키라인 72(패딩 12), 원 지름 84, 가로·세로 직사각형 80×64.
- 아이콘 크기와 터치 영역은 다르다. 버튼 안 아이콘은 52 / 40 / 32이다.

## 7. 디스플레이·폼팩터 [Display + Web Display Layout]

### 7.1 화면 유형과 앱 윈도 크기 [Web Display Layout 표, SVG 원본 판독]

| 타입 | 모델 | 비율 | 인치 | DPI | 전체 해상도 (dp) | 앱 Landscape (dp) | 앱 Portrait (dp) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| Standard | Connect-S-17 (Default) | 16:9 | 17 | 160 | 2560 × 1440 | 1700 × 1184 | 840 × 1184 |
| Standard | Connect-S-14.6 | 16:9 | 14.6 | 200 | 2048 × 1152 | 1360 × 948 | 672 × 948 |
| Standard | Connect-L-12.9 | 16:9 | 12.9 | 160 | 1920 × 1080 | 1274 × 855 | 628 × 855 |
| Wide | Connect-W-Standard | 9:2 | 24.6 | 160 | 3840 × 850 | 1640 × 810 | 800 × 810 |
| Wide | Connect-W-Extended | 24:9 | 24.6 | 160 | 3840 × 1440 | 1640 × 1400 | 800 × 1400 |
| Wide | Connect-WF (Waterfall) | 32:9 | 18.5 | 200 | 3072 × 816 | 1256 × 800 | 648 × 800 |

- 화면은 Status bar, Driving view, GNB, Application window로 구성된다. 앱은 1개 또는 2개가 동시에 노출되며, Landscape와 Portrait 두 레이아웃을 모두 지원해야 한다.
- Status bar, App library, 공조 패널, 키보드는 앱보다 상위 레이어라서 앱 윈도를 일시적으로 가릴 수 있다.
- **px → dp 결론.** 기본 기종 Connect-S-17은 160 DPI이고 2560×1440 dp이므로 1dp = 1px이다. Figma `connect-s` 캔버스 값은 그대로 **dp/sp로 사용**한다. 다른 기종의 값은 Figma 폼팩터 모드(`connect-l`, `connect-w`, `connect-waterfall`)에 있다.
- **차칵 레이아웃 기준.** 앱 Portrait 폭은 628~840dp, Landscape 폭은 1256~1700dp다. 두 열 전환 기준은 **≥1000dp 폭**이 적절하다. 현재 코드의 840dp는 S-17 Portrait(840)에서 두 열로 바뀌는 문제가 있다.

### 7.2 Figma 디스플레이 가이드 메모

- **Connect-S**(Standard, 17" / 14.6"), **Connect-L**(12.9"), **Connect-W**(Wide, 24.6" Standard / Extended), **Connect-WF**(Waterfall, 18.5")가 있다.
- Connect-S Landscape는 좌측 운전자 보조 영역과 앱 화면이 **1:2**로 나뉜다. Portrait에서는 실행된 앱이 중앙에 배치되어 **1:1:1**로 구성된다.
- Connect-W는 좌우로 길고 차량 상태에 따라 높이가 확장될 수 있다. Waterfall은 작은 비율 디스플레이다.
- 모든 폼팩터에 Light/Dark가 있다. 브랜드 테마 모드로 Hyundai·Kia·Genesis × Light/Dark가 존재한다.

## 8. 원문 간 불일치와 미결정 (TBD)

1. ~~px → dp 변환 비율~~ → **해결**(7.1): Figma connect-s px = dp. 단, 에뮬레이터의 실제 window bounds와 insets는 FormFactorConsole에서 확인한다.
2. **웹 이미지 오류.**
   - Text·Icon 표 5~8행이 `text_dark_*`로 중복 표기되어 있다. Figma 기준으로는 `text_light_*`다.
   - White/Gray Alpha 이미지는 Light와 Dark가 뒤바뀐 것으로 보인다. Figma 기준 `alpha/dark/*`는 Light에서 #131417 기반, Dark에서 #FFFFFF 기반이다.
   - `gearshitft_background` 오타, `charging_deep`의 Dark 값 모순, Media의 8자리 HEX 형식 혼용이 있다.
   - **값이 충돌하면 Figma를 우선한다.**
3. **상태 색.** Pleos에는 error·warning·success 의미 토큰이 없다. 차칵은 regulation/red·informative/positive·informative/active를 빌려 쓴다. 이는 차칵의 설계 선택이며 공식 매핑이 아니다.
4. **브랜드 테마.** Hyundai·Kia·Genesis 변형을 적용할지는 미정이다. MVP는 기본 Light/Dark만 사용한다.
5. **Asta Sans 번들링.** 라이선스(Google Fonts OFL 예상)를 확인한 뒤 `res/font`에 포함한다.
