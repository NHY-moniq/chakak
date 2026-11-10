> 자동 추출본 — 출처: https://document.pleos.ai/docs/connect/guide/docs-design/foundations/colors 이미지 판독
> 추출일: 2026-11-09. 값은 원본 그대로이며 앱 토큰으로 확정된 것이 아님. 요약은 ../design-foundations.md 참고.

## System Colors

> 출처: https://document.pleos.ai/docs/connect/guide/docs-design/foundations/colors (이미지 `system-color-*.png`에서 전사)
> 표시: 🟠 = 토큰명이 주황색(업데이트), 🔵 = 토큰명이 파란색(신규). 빈칸 = 검정(변경 없음).
> 값은 이미지에 적힌 텍스트를 그대로 옮겼다. 라벨과 견본 색이 어긋나 보이는 곳은 각 표 아래 "원문 주의"에 적었다(값은 고치지 않음).

### Background

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_50 | basic_50 | background_primary | 🟠 |
| basic_00 | basic_00 | background_secondary | 🟠 |
| basic_00 | basic_100 | background_popup | 🟠 |

### Surface

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_00 | basic_50 | surface_basic | 🟠 |
| basic_50 | basic_00 | surface_low | 🟠 |
| basic_100 | basic_100 | surface_high | 🟠 |
| basic_200 | basic_200 | surface_accent | |

### Text

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| static_dark_100 | static_light_100 | text_dark_primary | 🟠 |
| static_dark_200 | static_light_200 | text_dark_secondary | 🟠 |
| static_dark_300 | static_light_300 | text_dark_tertiary | 🟠 |
| static_dark_400 | static_light_400 | text_dark_quaternary | 🟠 |
| static_light_100 | static_dark_100 | text_dark_primary | 🟠 |
| static_light_200 | static_dark_200 | text_dark_secondary | 🟠 |
| static_light_300 | static_dark_300 | text_dark_tertiary | 🟠 |
| static_light_400 | static_dark_400 | text_dark_quaternary | 🟠 |

원문 주의: 5~8행 토큰명이 원본 이미지에서도 `text_dark_*`로 1~4행과 똑같다. 값이 반대이므로 원래는 `text_light_*`일 가능성이 높다(원문 오기 추정, 그대로 옮김).

### Icon

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| static_dark_100 | static_light_100 | icon_dark_primary | 🟠 |
| static_dark_200 | static_light_200 | icon_dark_secondary | 🟠 |
| static_dark_300 | static_light_300 | icon_dark_tertiary | 🟠 |
| static_dark_400 | static_light_400 | icon_dark_quaternary | 🟠 |
| static_light_100 | static_dark_100 | icon_dark_primary | 🟠 |
| static_light_200 | static_dark_200 | icon_dark_secondary | 🟠 |
| static_light_300 | static_dark_300 | icon_dark_tertiary | 🟠 |
| static_light_400 | static_dark_400 | icon_dark_quaternary | 🟠 |

원문 주의: Text와 같다. 5~8행이 `icon_dark_*`로 중복된다(원래는 `icon_light_*`로 추정).

### Button

| Light | Dark | Token Name | Old name | 표시 |
|---|---|---|---|---|
| alpha_dark_50 | alpha_dark_50 | button_basic_enabled | - | |
| alpha_dark_100 | alpha_dark_100 | button_basic_pressed | - | |
| alpha_light_200 | alpha_light_200 | button_basic_disabled | - | |
| basic_00 | alpha_dark_200 | button_switch_enabled | button-basic-active-enabled | 🟠 |
| alpha_light_500 | alpha_dark_200 | button_switch_pressed | button-basic-active-pressed | 🟠 |
| alpha_light_300 | alpha_dark_300 | button_switch_disabled | button-basic-active-disabled | 🟠 |
| basic_600 | basic_600 | button_filled_enabled | - | |
| basic_700 | basic_500 | button_filled_pressed | - | |
| alpha_dark_50 | alpha_dark_50 | button_filled_disabled | - | |

### Fields

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_00 | basic_00 | field_background | |
| basic_700 | basic_700 | field_focused | |

### Dropdown

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_00 | basic_alpha_dark100 | dropdown_normal | |
| basic_100 | basic_alpha_dark300 | dropdown_pressed | |

원문 주의: Dark 값은 이미지 표기 그대로 `basic_alpha_dark100` / `basic_alpha_dark300`이다(밑줄 없이 dark100). 다른 표의 `alpha_dark_100` 표기와 다르다.

### Switch

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| informative_active | informative_active | switch_on | 🟠 |
| alpha_dark_300 | alpha_dark_100 | switch_off | 🟠 |
| alpha_dark_100 | alpha_dark_100 | switch_disabled | 🟠 |
| basic_00 | basic_900 | switch_knob_on | 🟠 |
| basic_00 | basic_400 | switch_knob_off | 🟠 |
| basic_00 | alpha_dark_200 | switch_knob_disabled | 🔵 |

### Controller

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| alpha_dark_200 | alpha_dark_100 | controller_slider_normal | 🟠 |
| alpha_dark_300 | alpha_dark_200 | controller_slider_pressed | 🟠 |
| basic_00 | alpha_dark_300 | controller_slider_knob | 🟠 |
| alpha_dark_50 | alpha_dark_50 | controller_tab_background | 🟠 |
| basic_00 | alpha_light_100 | controller_stepper_background | 🟠 |

### Informative

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| #02C265 | #00E074 | informative_active | |
| #0064FF | #0082FF | informative_positive | |
| #FE3D16 | #FF4C28 | informative_negative | |
| #5A46FA | #6A57FF | informative_autonomous_driving | |

### Regulation

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| #0064FF | #0082FF | regulation_blue | |
| #00BA13 | #55F165 | regulation_green | |
| #FFC224 | #FFC224 | regulation_yellow | |
| #FF8A00 | #FF8A00 | regulation_orange | |
| #F62E24 | #FF4339 | regulation_red | |
| #0082FC | #0082FC | regulation_bluetooth | 🔵 |

### Driving

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_900 | basic_100 | gearshitft_background | 🔵 |
| #00DB25 | #00DB25 | charging_normal | 🔵 |
| #00B975 | #0082FC | charging_deep | 🔵 |
| basic_00 | basic_00 | gradient_end | |
| FFFFFF, 0% | 131417, 0% | gradient_start | |

원문 주의:
- `gearshitft_background`는 확대해서 확인한 원문 철자 그대로다(`gearshift` 오타로 보임).
- 1~3행은 토큰명뿐 아니라 Light/Dark 값 텍스트도 파란색이다.
- `charging_deep`의 Dark 라벨은 `#0082FC`인데, 견본 픽셀을 찍어 보면 `#00B975`(Light와 같은 초록)다. 라벨과 견본이 다르다.

### Dim

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| alpha_dark_50 | alpha_dark_50 | dim_dark_primary | 🟠 |
| alpha_dark_100 | alpha_dark_100 | dim_dark_secondary | 🟠 |
| alpha_dark_200 | alpha_dark_200 | dim_dark_tertiary | 🟠 |
| alpha_light_500 | alpha_light_500 | dim_light_primary | 🟠 |
| alpha_light_400 | alpha_light_400 | dim_light_secondary | 🟠 |
| alpha_light_300 | alpha_light_300 | dim_light_tertiary | 🟠 |

### Overlay

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| alpha_dark_200 | alpha_light_500 | overlay_default | 🟠 |

### Gleo AI

| Light | Dark | Token Name | Old name | 표시 |
|---|---|---|---|---|
| basic_00 | basic_200 | gleo_surface_primary | ambient_ai_surface_primary | 🟠 |
| alpha_dark_50 | alpha_light_200 | gleo_surface_secondary | ambient_ai_surface_secondary | 🟠 |
| #9BA8E2 | #545F93 | gleo_bubble_primary | ambient_ai_bubble_primary | 🟠 |
| #8896D3 | #3E4976 | gleo_bubble_secondary | ambient_ai_bubble_secondary | 🟠 |
| basic_00 | basic_100 | gleo_bubble_tertiary | ambient_ai_bubble_tertiary | 🟠 |
| #3F4B81 | #B6C1F3 | gleo_bubble_speaker | ambient_ai_bubble_speaker | 🟠 |

### Media

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| #C911E7FF | #C911E7FF | radio_primary | |
| #4781FFFF | #4781FFFF | music_primary | |
| #00FFFFFF | #001D1E21 | radio_gradient_start | |
| #FFFFFFFF | #FF1D1E21 | radio_gradient_end | |

원문 주의: 8자리 HEX의 알파 위치가 일정하지 않다. `#C911E7FF`와 `#4781FFFF`는 RRGGBBAA로 읽히고, `#00FFFFFF` / `#001D1E21` / `#FF1D1E21`은 AARRGGBB로 읽힌다(견본: radio_gradient_start는 투명, radio_gradient_end Dark는 불투명한 어두운 색).

### Phone

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| #32B957 | #3BD665 | phone_call_normal | 🟠 |
| #279E47 | #2DB752 | phone_call_pressed | 🟠 |
| #FE3D16 | #FF4C28 | phone_end_normal | 🟠 |
| #E73612 | #DF4323 | phone_end_pressed | 🟠 |

### Climate

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_200 | basic_50 | button_enabled | |
| basic_00 | basic_200 | button_selected | |
| alpha_dark_200 | alpha_dark_100 | button_border_enabled | |
| alpha_dark_50 | alpha_dark_50 | button_border_disabled | |
| alpha_dark_100 | alpha_light_300 | toggle_selected | |
| alpha_dark_200 | alpha_light_400 | toggle_pressed | |
| basic_00 | basic_100 | slider_background | |
| basic_00 | basic_600 | airvent_button_opened | |
| alpha_light_400 | alpha_dark_200 | airvent_button_closed | |
| static_dark_200 | static_dark_200 | airvent_symbol_opened | |
| static_dark_400 | static_dark_300 | airvent_symbol_closed | |
| static_light_300 | static_light_400 | airvent_opened | |
| alpha_light_200 | alpha_dark100 | airvent_closed | |

원문 주의: 마지막 행 Dark 값은 원문 그대로 `alpha_dark100`이다(밑줄 없음). 3~4행 Dark 칸에는 견본이 없다.

### Keyboard Normal

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_00 | basic_300 | keyboard_normal_primary_normal | 🔵 |
| basic_100 | basic_200 | keyboard_normal_primary_pressed | 🔵 |
| basic_300 | basic_200 | keyboard_normal_secondary_normal | 🔵 |
| basic_400 | basic_200 | keyboard_normal_secondary_pressed | 🔵 |
| basic_600 | basic_300 | keyboard_normal_active_normal | 🔵 |
| basic_700 | basic_200 | keyboard_normal_active_pressed | 🔵 |
| basic_600 | basic_500 | keyboard_normal_accents_normal | 🔵 |
| basic_700 | basic_400 | keyboard_normal_accents_pressed | 🔵 |
| basic_200 | basic_100 | keyboard_normal_background | 🔵 |

### Keyboard Static

| Light | Dark | Token Name | 표시 |
|---|---|---|---|
| basic_500 | basic_300 | keyboard_static_primary_normal | 🔵 |
| basic_600 | basic_200 | keyboard_static_primary_pressed | 🔵 |
| basic_600 | basic_200 | keyboard_static_secondary_normal | 🔵 |
| basic_500 | basic_200 | keyboard_static_secondary_pressed | 🔵 |
| basic_500 | basic_300 | keyboard_static_active_normal | 🔵 |
| basic_700 | basic_200 | keyboard_static_active_pressed | 🔵 |
| basic_300 | basic_500 | keyboard_static_accents_normal | 🔵 |
| basic_400 | basic_400 | keyboard_static_accents_pressed | 🔵 |
| basic_900 | basic_100 | keyboard_static_background | 🔵 |
| basic_00 | basic_400 | keyboard_static_icon_light | 🔵 |
| basic_900 | basic_400 | keyboard_static_icon_dark | 🔵 |
| alpha_light_100 | alpha_dark_100 | keyboard_static_icon_disabled | 🔵 |
| basic_700 | basic_400 | keyboard_static_text | 🔵 |

원문 주의: 이 표의 여러 견본은 라벨과 맞지 않아 보인다(예: icon_dark Dark `basic_400`인데 견본은 검정, text Light `basic_700`인데 견본은 흰색). 값은 라벨대로 옮겼다.
