> 자동 추출본 — 출처: docs/Pleos_Connect_design_assets_215.fig (canvas.fig kiwi 디코딩, tools/tokens.mjs)
> 추출일: 2026-11-09. 값은 원본 그대로이며 앱 토큰으로 확정된 것이 아님. 요약은 ../design-foundations.md 참고.

# Design assets 2.1.5

## Variables

### Color ⟨9023bf⟩ — 22개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| Button/Call/accept-normal | COLOR | #32B957 | #3BD665 |
| Button/Call/accept-pressed | COLOR | #279E47 | #2DB752 |
| Button/Call/end-normal | COLOR | #FE3D16 | #FF4C28 |
| Button/Call/end-pressed | COLOR | #E73612 | #DF4323 |
| Feature/Media/music-primary | COLOR | #4781FF | #4781FF |
| Feature/Media/radio-primary | COLOR | #C911E7 | #C911E7 |
| Gray/00 | COLOR | #FFFFFF | #131417 |
| Gray/400 | COLOR | #A4A8AE | #A4A8AE |
| Gray/50 | COLOR | #F7F8FA | #1D1E21 |
| Gray/900 | COLOR | #131417 | #FFFFFF |
| GrayAlpha/100 | COLOR | #131417 α10% | #FFFFFF α10% |
| GrayAlpha/200 | COLOR | #131417 α20% | #FFFFFF α20% |
| Informative/active | COLOR | #02C265 | #00E074 |
| Miscellaneous/Charging/deep | COLOR | #00B975 | #00B975 |
| Miscellaneous/Charging/normal | COLOR | #00DB25 | #00DB25 |
| Miscellaneous/Switch/background-on | COLOR | #02C265 ← Informative/active | #00E074 ← Informative/active |
| Miscellaneous/Switch/button-disabled | COLOR | #FFFFFF ← Gray/00 | #FFFFFF α20% ← GrayAlpha/200 |
| Miscellaneous/Switch/button-off | COLOR | #FFFFFF ← Gray/00 | #A4A8AE ← Gray/400 |
| Miscellaneous/Switch/button-on | COLOR | #FFFFFF ← Gray/00 | #FFFFFF ← Gray/900 |
| Miscellaneous/Textfields/background | COLOR | #FFFFFF ← Gray/00 | #131417 ← Gray/00 |
| Static/white-primary | COLOR | #FFFFFF | #FFFFFF |
| Surface/container | COLOR | #FFFFFF ← Gray/00 | #1D1E21 ← Gray/50 |

### Primitives ⟨d1c4e9⟩ — 12개, modes: Value

| name | type | Value |
|---|---|---|
| Color/Base/Black | COLOR | #000000 |
| Color/Base/White | COLOR | #FFFFFF |
| Color/Cool Gray/100 | COLOR | #E2E5EB |
| Color/Cool Gray/200 | COLOR | #C3C9D1 |
| Color/Cool Gray/300 | COLOR | #A2ABB8 |
| Color/Cool Gray/500 | COLOR | #626D7C |
| Color/Cool Gray/700 | COLOR | #343B45 |
| Color/Cool Gray/800 | COLOR | #22262E |
| Spacing/spacing-03 | FLOAT | 8 |
| Spacing/spacing-04 | FLOAT | 12 |
| Spacing/spacing-05 | FLOAT | 16 |
| Spacing/spacing-07 | FLOAT | 32 |

### [Theme] ◑ Color ⟨47e7fe⟩ — 7개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| Border/border-primary | COLOR | #C3C9D1 ← Color/Cool Gray/200 | #343B45 ← Color/Cool Gray/700 |
| Border/border-primary-muted | COLOR | #E2E5EB ← Color/Cool Gray/100 | #22262E ← Color/Cool Gray/800 |
| Icon/icon-primary | COLOR | #000000 ← Color/Base/Black | #FFFFFF ← Color/Base/White |
| Icon/icon-secondary | COLOR | #626D7C ← Color/Cool Gray/500 | #626D7C ← Color/Cool Gray/500 |
| Text/text-primary | COLOR | #000000 ← Color/Base/Black | #FFFFFF ← Color/Base/White |
| Text/text-primary-muted | COLOR | #A2ABB8 ← Color/Cool Gray/300 | #343B45 ← Color/Cool Gray/700 |
| Text/text-secondary | COLOR | #626D7C ← Color/Cool Gray/500 | #626D7C ← Color/Cool Gray/500 |

### Color ⟨d1b3a9⟩ — 2개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| Gray/900 | COLOR | #131417 | #FFFFFF |
| Icon/gray-primary | COLOR | #131417 ← Gray/900 | #FFFFFF ← Gray/900 |

### Color ⟨13cebf⟩ — 22개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| _Feature/Driving/Normal/Alert | COLOR | #FF8A00 | #FF8A00 |
| _Feature/Driving/Normal/Danger | COLOR | #FF2E00 | #FF2E00 |
| _Feature/Driving/Normal/Positive | COLOR | #00BA13 | #00BA13 |
| _Feature/Driving/Normal/Warning | COLOR | #FFA133 | #FFA133 |
| _Legacy/Scale/Black/Black | COLOR | #000000 | #FFFFFF |
| _Legacy/Scale/White/White | COLOR | #FFFFFF | #000000 |
| _Legacy/Solid/Cool/900 | COLOR | #1B1D21 | #F0F1F5 |
| Button/Call/accept-normal | COLOR | #32B957 | #3BD665 |
| Button/Call/end-normal | COLOR | #FE3D16 | #FF4C28 |
| Gray/00 | COLOR | #FFFFFF | #131417 |
| Gray/900 | COLOR | #131417 | #FFFFFF |
| Icon/gray-primary | COLOR | #131417 ← Gray/900 | #FFFFFF ← Gray/900 |
| Icon/gray-quaternary | COLOR | #131417 α32% | #FFFFFF α32% |
| Icon/gray-tertiary | COLOR | #131417 α64% | #FFFFFF α64% |
| Icon/white-primary | COLOR | #FFFFFF ← Gray/00 | #131417 ← Gray/00 |
| Informative/autonomous driving | COLOR | #5A46FA | #6A57FF |
| Regulation/blue | COLOR | #0064FF | #0082FF |
| Regulation/green | COLOR | #00BA13 | #55F165 |
| Regulation/orange | COLOR | #FF8A00 | #FF8A00 |
| Regulation/red | COLOR | #F62E24 | #FF4339 |
| System/Grayscale/Opaque/Black | COLOR | #000000 | #000000 |
| System/Grayscale/Opaque/White | COLOR | #FFFFFF | #FFFFFF |

### Color ⟨3b4c36⟩ — 10개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| _Legacy/Scale/Black/Black | COLOR | #000000 | #FFFFFF |
| color/basic/00 | COLOR | #FFFFFF | #131417 |
| color/basic/900 | COLOR | #131417 | #FFFFFF |
| icon/dark/primary | COLOR | #131417 ← static/dark/100 | #FFFFFF ← static/light/100 |
| icon/light/primary | COLOR | #FFFFFF ← static/light/100 | #131417 ← static/dark/100 |
| static/dark/100 | COLOR | #131417 | #131417 |
| static/light/100 | COLOR | #FFFFFF | #FFFFFF |

### ivi ⟨d70340⟩ — 1개, modes: Eng / Kor

| name | type | Eng | Kor |
|---|---|---|---|
| New group/ivi_radio | STRING | "Radio" | "라디오" |

### Color ⟨994235⟩ — 134개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| _Feature/Driving/Normal/Positive | COLOR | #00BA13 | #00BA13 |
| _Legacy/Scale/Black/Black | COLOR | #000000 | #FFFFFF |
| _Legacy/Scale/White/White | COLOR | #FFFFFF | #000000 |
| _Legacy/Solid/Cool/500 | COLOR | #626675 | #626675 |
| _Legacy/Solid/Cool/900 | COLOR | #1B1D21 | #F0F1F5 |
| _Legacy/Solid/Warm/300 | COLOR | #C8C8C8 | #353535 |
| _System/Brand/Primary/Normal | COLOR | #5A46FA | #6A57FF |
| alpha/dark/100 | COLOR | #131417 α10% | #FFFFFF α10% |
| alpha/dark/200 | COLOR | #131417 α20% | #FFFFFF α20% |
| alpha/dark/300 | COLOR | #131417 α30% | #FFFFFF α30% |
| alpha/dark/400 | COLOR | #131417 α40% | #FFFFFF α40% |
| alpha/dark/50 | COLOR | #131417 α5% | #FFFFFF α5% |
| alpha/dark/500 | COLOR | #131417 α50% | #FFFFFF α50% |
| alpha/light/100 | COLOR | #FFFFFF α10% | #131417 α10% |
| alpha/light/200 | COLOR | #FFFFFF α20% | #131417 α20% |
| alpha/light/300 | COLOR | #FFFFFF α30% | #131417 α30% |
| alpha/light/400 | COLOR | #FFFFFF α40% | #131417 α40% |
| alpha/light/50 | COLOR | #FFFFFF α5% | #131417 α5% |
| alpha/light/500 | COLOR | #FFFFFF α50% | #131417 α50% |
| background/popup | COLOR | #FFFFFF ← basic/00 | #313236 ← basic/100 |
| background/primary | COLOR | #F7F8FA ← basic/50 | #1D1E21 ← basic/50 |
| background/secondary | COLOR | #FFFFFF ← basic/00 | #131417 ← basic/00 |
| basic/00 | COLOR | #FFFFFF | #131417 |
| basic/100 | COLOR | #EDEEF2 | #313236 |
| basic/200 | COLOR | #E0E1E6 | #44464E |
| basic/300 | COLOR | #C7C9CE | #686A72 |
| basic/400 | COLOR | #A4A8AE | #A4A8AE |
| basic/50 | COLOR | #F7F8FA | #1D1E21 |
| basic/500 | COLOR | #686A72 | #C7C9CE |
| basic/600 | COLOR | #44464E | #E0E1E6 |
| basic/700 | COLOR | #313236 | #EDEEF2 |
| basic/800 | COLOR | #1D1E21 | #F7F8FA |
| basic/900 | COLOR | #131417 | #FFFFFF |
| Brand/Teritary/Black | COLOR | #000000 ← _Legacy/Scale/Black/Black | #000000 ← _Legacy/Scale/White/White |
| button/basic/disabled | COLOR | #FFFFFF α20% ← alpha/light/200 | #131417 α20% ← alpha/light/200 |
| button/basic/enabled | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| button/basic/pressed | COLOR | #131417 α10% ← alpha/dark/100 | #FFFFFF α10% ← alpha/dark/100 |
| button/filled/disabled | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| button/filled/enabled | COLOR | #44464E ← basic/600 | #E0E1E6 ← basic/600 |
| button/filled/pressed | COLOR | #313236 ← basic/700 | #C7C9CE ← basic/500 |
| button/switch/disabled | COLOR | #FFFFFF α30% ← alpha/light/300 | #131417 α30% ← alpha/light/300 |
| button/switch/enabled | COLOR | #FFFFFF ← basic/00 | #FFFFFF α20% ← alpha/dark/200 |
| button/switch/pressed | COLOR | #FFFFFF α50% ← alpha/light/500 | #FFFFFF α10% ← alpha/dark/100 |
| climate/button_border_enabled | COLOR | #131417 α20% ← alpha/dark/200 | #FFFFFF α10% ← alpha/dark/100 |
| climate/slider_background | COLOR | #FFFFFF ← basic/00 | #313236 ← basic/100 |
| color/feature/driving_view/mask/filled_secondary | COLOR | #F7F8FA α0% | #1D1E21 α0% |
| color/feature/driving_view/mask/subtle_secondary | COLOR | #F7F8FA ← basic/50 | #1D1E21 ← basic/50 |
| Common/Empty/Content | COLOR | #C8C8C8 ← _Legacy/Solid/Warm/300 | #626675 ← _Legacy/Solid/Cool/500 |
| controller/slider/knop | COLOR | #FFFFFF ← basic/00 | #FFFFFF α30% ← alpha/dark/300 |
| controller/slider/normal | COLOR | #131417 α20% ← alpha/dark/200 | #FFFFFF α10% ← alpha/dark/100 |
| controller/slider/pressed | COLOR | #131417 α30% ← alpha/dark/300 | #FFFFFF α20% ← alpha/dark/200 |
| controller/stepper_background | COLOR | #FFFFFF ← basic/00 | #FFFFFF α10% ← alpha/dark/100 |
| controller/tab_background | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| dim/dark/primary | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| dim/dark/secondary | COLOR | #131417 α10% ← alpha/dark/100 | #FFFFFF α10% ← alpha/dark/100 |
| dim/dark/tertiary | COLOR | #131417 α20% ← alpha/dark/200 | #FFFFFF α20% ← alpha/dark/200 |
| dim/light/primary | COLOR | #FFFFFF α50% ← alpha/light/500 | #131417 α50% ← alpha/light/500 |
| dim/light/secondary | COLOR | #FFFFFF α40% ← alpha/light/400 | #131417 α40% ← alpha/light/400 |
| dim/light/tertiary | COLOR | #FFFFFF α30% ← alpha/light/300 | #131417 α30% ← alpha/light/300 |
| divider/primary | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| divider/secondary | COLOR | #131417 α10% ← alpha/dark/100 | #FFFFFF α10% ← alpha/dark/100 |
| divider/tertiary | COLOR | #131417 α20% ← alpha/dark/200 | #FFFFFF α20% ← alpha/dark/200 |
| driving_view/gradient_start | COLOR | #FFFFFF α0% | #131417 α0% |
| dropdown/normal | COLOR | #FFFFFF ← basic/00 | #FFFFFF α10% ← alpha/dark/100 |
| dropdown/pressed | COLOR | #EDEEF2 ← basic/100 | #FFFFFF α30% ← alpha/dark/300 |
| field/background | COLOR | #FFFFFF ← basic/00 | #131417 ← basic/00 |
| field/focused | COLOR | #313236 ← basic/700 | #EDEEF2 ← basic/700 |
| gleo/Olf/surface_primary | COLOR | #FFFFFF ← basic/00 | #44464E ← basic/200 |
| icon/dark/primary | COLOR | #131417 ← static/dark/100 | #FFFFFF ← static/light/100 |
| icon/dark/quaternary | COLOR | #131417 α32% ← static/dark/400 | #FFFFFF α32% ← static/light/400 |
| icon/dark/secondary | COLOR | #131417 α84% ← static/dark/200 | #FFFFFF α84% ← static/light/200 |
| icon/dark/tertiary | COLOR | #131417 α64% ← static/dark/300 | #FFFFFF α64% ← static/light/300 |
| icon/light/primary | COLOR | #FFFFFF ← static/light/100 | #131417 ← static/dark/100 |
| icon/light/quaternary | COLOR | #FFFFFF α32% ← static/light/400 | #131417 α32% ← static/dark/400 |
| icon/light/secondary | COLOR | #FFFFFF α84% ← static/light/200 | #131417 α84% ← static/dark/200 |
| icon/light/tertiary | COLOR | #FFFFFF α64% ← static/light/300 | #131417 α64% ← static/dark/300 |
| informative/active | COLOR | #02C265 | #00E074 |
| informative/autonomous_driving | COLOR | #5A46FA | #6A57FF |
| informative/positive | COLOR | #0064FF | #0082FF |
| keyboard/normal/accents_normal | COLOR | #44464E ← basic/600 | #C7C9CE ← basic/500 |
| keyboard/normal/accents_pressed | COLOR | #313236 ← basic/700 | #A4A8AE ← basic/400 |
| keyboard/normal/active_normal | COLOR | #44464E ← basic/600 | #686A72 ← basic/300 |
| keyboard/normal/active_pressed | COLOR | #313236 ← basic/700 | #44464E ← basic/200 |
| keyboard/normal/background | COLOR | #E0E1E6 ← basic/200 | #313236 ← basic/100 |
| keyboard/normal/primary_normal | COLOR | #FFFFFF ← basic/00 | #686A72 ← basic/300 |
| keyboard/normal/primary_pressed | COLOR | #EDEEF2 ← basic/100 | #44464E ← basic/200 |
| keyboard/normal/secondary_normal | COLOR | #C7C9CE ← basic/300 | #44464E ← basic/200 |
| keyboard/normal/secondary_pressed | COLOR | #A4A8AE ← basic/400 | #686A72 ← basic/300 |
| keyboard/static/accents_normal | COLOR | #C7C9CE ← basic/300 | #C7C9CE ← basic/500 |
| keyboard/static/accents_pressed | COLOR | #A4A8AE ← basic/400 | #A4A8AE ← basic/400 |
| keyboard/static/active_normal | COLOR | #686A72 ← basic/500 | #686A72 ← basic/300 |
| keyboard/static/active_pressed | COLOR | #313236 ← basic/700 | #44464E ← basic/200 |
| keyboard/static/background | COLOR | #131417 ← basic/900 | #131417 ← basic/00 |
| keyboard/static/icon_dark | COLOR | #131417 ← basic/900 | #131417 ← basic/00 |
| keyboard/static/icon_disabled | COLOR | #FFFFFF α10% ← alpha/light/100 | #FFFFFF α10% ← alpha/dark/100 |
| keyboard/static/icon_light | COLOR | #FFFFFF ← basic/00 | #FFFFFF ← basic/900 |
| keyboard/static/primary_normal | COLOR | #686A72 ← basic/500 | #686A72 ← basic/300 |
| keyboard/static/primary_pressed | COLOR | #44464E ← basic/600 | #44464E ← basic/200 |
| keyboard/static/secondary_normal | COLOR | #44464E ← basic/600 | #44464E ← basic/200 |
| keyboard/static/secondary_pressed | COLOR | #686A72 ← basic/500 | #686A72 ← basic/300 |
| keyboard/static/text | COLOR | #FFFFFF ← basic/00 | #FFFFFF ← basic/900 |
| media/radio_gradient_end | COLOR | #FFFFFF | #1D1E21 |
| media/radio_gradient_start | COLOR | #FFFFFF α0% | #1D1E21 α0% |
| overlay/default | COLOR | #131417 α20% ← alpha/dark/200 | #131417 α50% ← alpha/light/500 |
| phone/accept_normal | COLOR | #32B957 | #3BD665 |
| phone/end_normal | COLOR | #FE3D16 | #FF4C28 |
| regulation/blue | COLOR | #0064FF | #0082FF |
| regulation/red | COLOR | #F62E24 | #FF4339 |
| static/dark/100 | COLOR | #131417 | #131417 |
| static/dark/200 | COLOR | #131417 α84% | #131417 α84% |
| static/dark/300 | COLOR | #131417 α64% | #131417 α64% |
| static/dark/400 | COLOR | #131417 α32% | #131417 α32% |
| static/light/100 | COLOR | #FFFFFF | #FFFFFF |
| static/light/200 | COLOR | #FFFFFF α84% | #FFFFFF α84% |
| static/light/300 | COLOR | #FFFFFF α64% | #FFFFFF α64% |
| static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% |
| surface/accent | COLOR | #E0E1E6 ← basic/200 | #44464E ← basic/200 |
| surface/basic | COLOR | #FFFFFF ← basic/00 | #1D1E21 ← basic/50 |
| surface/high | COLOR | #EDEEF2 ← basic/100 | #313236 ← basic/100 |
| switch/disabled | COLOR | #131417 α10% ← alpha/dark/100 | #FFFFFF α10% ← alpha/dark/100 |
| switch/knob_disabled | COLOR | #FFFFFF ← basic/00 | #FFFFFF α20% ← alpha/dark/200 |
| switch/knob_off | COLOR | #FFFFFF ← basic/00 | #A4A8AE ← basic/400 |
| switch/knob_on | COLOR | #FFFFFF ← basic/00 | #FFFFFF ← basic/900 |
| switch/off | COLOR | #131417 α30% ← alpha/dark/300 | #FFFFFF α10% ← alpha/dark/100 |
| switch/on | COLOR | #02C265 ← informative/active | #00E074 ← informative/active |
| System/Grayscale/Opaque/White | COLOR | #FFFFFF | #FFFFFF |
| text/dark/primary | COLOR | #131417 ← static/dark/100 | #FFFFFF ← static/light/100 |
| text/dark/quaternary | COLOR | #131417 α32% ← static/dark/400 | #FFFFFF α32% ← static/light/400 |
| text/dark/secondary | COLOR | #131417 α84% ← static/dark/200 | #FFFFFF α84% ← static/light/200 |
| text/dark/tertiary | COLOR | #131417 α64% ← static/dark/300 | #FFFFFF α64% ← static/light/300 |
| text/light/primary | COLOR | #FFFFFF ← static/light/100 | #131417 ← static/dark/100 |
| text/light/quaternary | COLOR | #FFFFFF α32% ← static/light/400 | #131417 α32% ← static/dark/400 |
| text/light/secondary | COLOR | #FFFFFF α84% ← static/light/200 | #131417 α84% ← static/dark/200 |
| text/light/tertiary | COLOR | #FFFFFF α64% ← static/light/300 | #131417 α64% ← static/dark/300 |

### form_factor ⟨96b6cf⟩ — 452개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| app_market/header/title/height | FLOAT | 80 | 70 | 66 | 85 |
| bars/app_control/container_height | FLOAT | 60 | 53 | 100 | 128 |
| bars/app_control/container_width | FLOAT | 320 | 282 | 266 | 340 |
| bars/app_control/handle_height | FLOAT | 8 | 7 | 8 | 9 |
| bars/app_control/handle_width_normal | FLOAT | 140 | 123 | 116 | 148 |
| bars/app_control/padding_top | FLOAT | 16 | 14 | 14 | 17 |
| bars/page_scroll/min_height | FLOAT | 48 | 42 | 40 | 51 |
| bars/page_scroll/width | FLOAT | 6 | 5 | 6 | 6 |
| bars/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| bars/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| bars/radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| bars/radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| buttons/basic/disabled_border | FLOAT | 2 | 2 | 2 | 3 |
| buttons/basic/large/gap_horizontal | FLOAT | 16 | 14 | 14 | 17 |
| buttons/basic/large/height | FLOAT | 100 | 88 | 84 | 106 |
| buttons/basic/large/icon_sqaure | FLOAT | 52 | 46 | 43 | 55 |
| buttons/basic/large/min_width | FLOAT | 140 | 123 | 116 | 148 |
| buttons/basic/large/padding_horizontal | FLOAT | 40 | 35 | 34 | 42 |
| buttons/basic/large/padding_vertical | FLOAT | 16 | 14 | 14 | 17 |
| buttons/basic/large/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| buttons/basic/medium/gap_horizontal | FLOAT | 14 | 12 | 12 | 15 |
| buttons/basic/medium/height | FLOAT | 80 | 70 | 66 | 84 |
| buttons/basic/medium/icon_sqaure | FLOAT | 40 | 35 | 33 | 42 |
| buttons/basic/medium/min_width | FLOAT | 120 | 106 | 100 | 128 |
| buttons/basic/medium/padding_horizontal | FLOAT | 32 | 28 | 27 | 35 |
| buttons/basic/medium/padding_vertical | FLOAT | 16 | 14 | 14 | 17 |
| buttons/basic/medium/radius_bottom_left_medium | FLOAT | 24 | 21 | 20 | 26 |
| buttons/basic/medium/radius_bottom_right_medium | FLOAT | 24 | 21 | 20 | 26 |
| buttons/basic/medium/radius_top_left_medium | FLOAT | 24 | 21 | 20 | 26 |
| buttons/basic/medium/radius_top_right_medium | FLOAT | 24 | 21 | 20 | 26 |
| buttons/basic/small/gap_horizontal | FLOAT | 12 | 11 | 10 | 13 |
| buttons/basic/small/height | FLOAT | 64 | 56 | 54 | 68 |
| buttons/basic/small/icon_sqaure | FLOAT | 32 | 28 | 27 | 35 |
| buttons/basic/small/min_width | FLOAT | 100 | 88 | 84 | 106 |
| buttons/basic/small/padding_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| buttons/basic/small/padding_vertical | FLOAT | 8 | 7 | 8 | 10 |
| buttons/basic/small/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| buttons/icon/circle_radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/circle_radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/circle_radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/circle_radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/medium/content | FLOAT | 56 | 49 | 46 | 59 |
| buttons/icon/medium/height | FLOAT | 80 | 70 | 66 | 84 |
| buttons/icon/medium/rounded_radius_bottom_left | FLOAT | 20 | 18 | 17 | 22 |
| buttons/icon/medium/rounded_radius_bottom_right | FLOAT | 20 | 18 | 17 | 22 |
| buttons/icon/medium/rounded_radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| buttons/icon/medium/rounded_radius_top_right | FLOAT | 20 | 18 | 17 | 22 |
| buttons/icon/medium/width | FLOAT | 80 | 70 | 66 | 84 |
| buttons/icon/small/content | FLOAT | 44 | 39 | 37 | 49 |
| buttons/icon/small/height | FLOAT | 64 | 56 | 54 | 68 |
| buttons/icon/small/rounded_radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| buttons/icon/small/rounded_radius_bottom_right | FLOAT | 16 | 14 | 1613 | 17 |
| buttons/icon/small/rounded_radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| buttons/icon/small/rounded_radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| buttons/icon/small/width | FLOAT | 64 | 56 | 54 | 68 |
| buttons/keypad/border | FLOAT | 4 | 4 | 3 | 4 |
| buttons/keypad/height | FLOAT | 108 | 95 | 80 | 116 |
| buttons/keypad/icon | FLOAT | 48 | 42 | 40 | 51 |
| buttons/keypad/min_width | FLOAT | 200 | 176 | 166 | 212 |
| buttons/keypad/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| buttons/keypad/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| buttons/keypad/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| buttons/keypad/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| buttons/text/medium/gap | FLOAT | 8 | 7 | 8 | 9 |
| buttons/text/medium/icon | FLOAT | 32 | 28 | 27 | 35 |
| buttons/text/small/gap | FLOAT | 4 | 4 | 4 | 4 |
| buttons/text/small/icon | FLOAT | 28 | 25 | 23 | 29 |
| buttons/toggle/disabled_border | FLOAT | 2 | 2 | 2 | 3 |
| buttons/toggle/large/gap_horizontal | FLOAT | 16 | 14 | 14 | 17 |
| buttons/toggle/large/height | FLOAT | 100 | 88 | 84 | 106 |
| buttons/toggle/large/icon_sqaure | FLOAT | 52 | 46 | 43 | 55 |
| buttons/toggle/large/min_width | FLOAT | 140 | 123 | 116 | 148 |
| buttons/toggle/large/padding_horizontal | FLOAT | 40 | 35 | 34 | 42 |
| buttons/toggle/large/padding_vertical | FLOAT | 16 | 14 | 14 | 17 |
| buttons/toggle/large/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| buttons/toggle/large/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| buttons/toggle/large/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| buttons/toggle/large/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| buttons/toggle/medium/gap_horizontal | FLOAT | 14 | 12 | 12 | 15 |
| buttons/toggle/medium/height | FLOAT | 80 | 70 | 66 | 84 |
| buttons/toggle/medium/icon_square | FLOAT | 40 | 35 | 33 | 42 |
| buttons/toggle/medium/min_width | FLOAT | 120 | 106 | 100 | 128 |
| buttons/toggle/medium/padding_horizontal | FLOAT | 32 | 28 | 28 | 35 |
| buttons/toggle/medium/padding_vertical | FLOAT | 16 | 14 | 14 | 16 |
| buttons/toggle/medium/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| buttons/toggle/medium/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| buttons/toggle/medium/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| buttons/toggle/medium/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| buttons/toggle/small/gap_horizontal | FLOAT | 12 | 11 | 10 | 12 |
| buttons/toggle/small/height | FLOAT | 64 | 56 | 54 | 68 |
| buttons/toggle/small/icon_sqaure | FLOAT | 32 | 28 | 27 | 35 |
| buttons/toggle/small/min_width | FLOAT | 100 | 88 | 84 | 106 |
| buttons/toggle/small/padding_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| buttons/toggle/small/padding_vertical | FLOAT | 8 | 7 | 8 | 10 |
| buttons/toggle/small/radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| buttons/toggle/small/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| buttons/toggle/small/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| buttons/toggle/small/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/border_large | FLOAT | 4 | 4 | 3 | 4 |
| controller/slider/border_medium | FLOAT | 3 | 3 | 3 | 3 |
| controller/slider/border_small | FLOAT | 2 | 2 | 2 | 3 |
| controller/slider/centered/knob/enabled_circle | FLOAT | 48 | 42 | 40 | 51 |
| controller/slider/centered/knob/pressed_circle | FLOAT | 56 | 49 | 46 | 59 |
| controller/slider/centered/temporature/text_height | FLOAT | 62 | 55 | 52 | 64 |
| controller/slider/centered/track/height | FLOAT | 8 | 7 | 8 | 9 |
| controller/slider/centered/track/min_width | FLOAT | 320 | 282 | 266 | 340 |
| controller/slider/centered/track/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| controller/slider/centered/track/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| controller/slider/centered/track/radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| controller/slider/centered/track/radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| controller/slider/continuous/medium/button_medium | FLOAT | 80 | 70 | 66 | 84 |
| controller/slider/continuous/medium/icon_medium | FLOAT | 56 | 49 | 46 | 59 |
| controller/slider/continuous/medium/knob/radius_bottom_left | FLOAT | 20 | 20 | 17 | 22 |
| controller/slider/continuous/medium/knob/radius_bottom_right | FLOAT | 20 | 20 | 17 | 22 |
| controller/slider/continuous/medium/knob/radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| controller/slider/continuous/medium/knob/radius_top_right | FLOAT | 20 | 20 | 17 | 22 |
| controller/slider/continuous/medium/label_width | FLOAT | 180 | 158 | 180 | 180 |
| controller/slider/continuous/medium/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/continuous/medium/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/continuous/medium/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/continuous/medium/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/continuous/small/button_small | FLOAT | 72 | 63 | 60 | 77 |
| controller/slider/continuous/small/icon_small | FLOAT | 48 | 42 | 40 | 51 |
| controller/slider/continuous/small/knob/radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/knob/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/knob/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/knob/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/small/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/small/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/small/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/track/medium/icon | FLOAT | 48 | 42 | 40 | 51 |
| controller/slider/continuous/track/medium/item_padding | FLOAT | 4 | 4 | 4 | 4 |
| controller/slider/continuous/track/medium/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/track/medium/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/track/medium/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/track/medium/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| controller/slider/continuous/track/small/icon | FLOAT | 40 | 35 | 33 | 42 |
| controller/slider/continuous/track/small/item_padding | FLOAT | 4 | 4 | 4 | 4 |
| controller/slider/continuous/track/small/radius_bottom_left | FLOAT | 20 | 18 | 17 | 22 |
| controller/slider/continuous/track/small/radius_bottom_right | FLOAT | 20 | 18 | 17 | 22 |
| controller/slider/continuous/track/small/radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| controller/slider/continuous/track/small/radius_top_right | FLOAT | 20 | 18 | 17 | 22 |
| controller/slider/gap_horizontal_large | FLOAT | 16 | 14 | 14 | 17 |
| controller/slider/gap_horizontal_small | FLOAT | 12 | 11 | 10 | 13 |
| controller/slider/height_large | FLOAT | 100 | 88 | 84 | 106 |
| controller/slider/height_medium | FLOAT | 80 | 70 | 66 | 84 |
| controller/slider/height_small | FLOAT | 72 | 67 | 64 | 81 |
| controller/slider/padding_horizontal_large | FLOAT | 40 | 35 | 34 | 42 |
| controller/slider/padding_horizontal_medium | FLOAT | 20 | 18 | 18 | 22 |
| controller/slider/padding_horizontal_small | FLOAT | 10 | 9 | 8 | 10 |
| controller/slider/padding_vertical_large | FLOAT | 12 | 11 | 10 | 13 |
| controller/slider/padding_vertical_medium | FLOAT | 10 | 9 | 8 | 10 |
| controller/slider/padding_vertical_small | FLOAT | 8 | 7 | 8 | 9 |
| controller/slider/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| controller/slider/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| controller/stepper/border | FLOAT | 2 | 2 | 2 | 3 |
| controller/stepper/divider | FLOAT | 2 | 2 | 2 | 3 |
| controller/stepper/gap_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| controller/stepper/height | FLOAT | 100 | 88 | 84 | 106 |
| controller/stepper/padding_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| controller/stepper/padding_vertical | FLOAT | 18 | 16 | 15 | 19 |
| controller/stepper/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| controller/stepper/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| controller/stepper/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| controller/stepper/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| controller/stepper/width | FLOAT | 420 | 352 | 340 | 426 |
| dropdown/border | FLOAT | 2 | 2 | 2 | 3 |
| dropdown/container/prefix_app_icon_border | FLOAT | 1 ← line/level1 | 1 ← line/level1 | 1 ← line/level1 | 1 ← line/level1 |
| dropdown/gap_vertical_medium | FLOAT | 16 | 14 | 14 | 17 |
| dropdown/gap_vertical_small | FLOAT | 8 | 7 | 8 | 9 |
| dropdown/medium/height | FLOAT | 100 | 88 | 84 | 106 |
| dropdown/medium/prefix/icon_square | FLOAT | 40 ← square/level8 | 35 | 33 ← square/level8 | 40 ← square/level8 |
| dropdown/medium/prefix/padding_horizontal | FLOAT | 4 | 4 | 4 | 4 |
| dropdown/medium/prefix/padding_vertical | FLOAT | 4 | 4 | 4 | 4 |
| dropdown/medium/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| dropdown/medium/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| dropdown/medium/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| dropdown/medium/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| dropdown/padding_large | FLOAT | 20 | 18 | 18 | 22 |
| dropdown/padding_medium | FLOAT | 16 | 14 | 14 | 17 |
| dropdown/padding_small | FLOAT | 12 | 11 | 10 | 13 |
| dropdown/prefix/app_radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| dropdown/prefix/app_radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| dropdown/prefix/app_radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| dropdown/prefix/app_radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| dropdown/prefix/container_square | FLOAT | 56 | 49 | 46 | 59 |
| dropdown/prefix/medium/app_icon_radius_bottom_left | FLOAT | 12 ← radius/bottom_left/level4 | 11 | 10 ← radius/bottom_left/level4 | 12 ← radius/bottom_left/level4 |
| dropdown/prefix/medium/app_icon_radius_bottom_right | FLOAT | 12 ← radius/bottom_right/level4 | 11 | 10 ← radius/bottom_right/level4 | 12 ← radius/bottom_right/level4 |
| dropdown/prefix/medium/app_icon_radius_top_left | FLOAT | 12 ← radius/top_left/level4 | 11 | 10 ← radius/top_left/level4 | 12 ← radius/top_left/level4 |
| dropdown/prefix/medium/app_icon_radius_top_right | FLOAT | 12 ← radius/top_right/level4 | 11 | 10 ← radius/top_right/level4 | 12 ← radius/top_right/level4 |
| dropdown/small/height | FLOAT | 80 | 70 | 66 | 84 |
| dropdown/small/radius_bottom_left | FLOAT | 28 | 25 | 23 | 29 |
| dropdown/small/radius_bottom_right | FLOAT | 28 | 25 | 23 | 29 |
| dropdown/small/radius_top_left | FLOAT | 28 | 25 | 23 | 29 |
| dropdown/small/radius_top_right | FLOAT | 28 | 25 | 23 | 29 |
| dropdown/suffix/dot_icon_square | FLOAT | 16 | 14 | 13 | 17 |
| dropdown/suffix/icon_square | FLOAT | 48 | 42 | 40 | 51 |
| fields/number/border | FLOAT | 4 | 4 | 3 | 4 |
| fields/number/cursor_height | FLOAT | 48 | 42 | 40 | 51 |
| fields/number/cursor_width | FLOAT | 4 | 4 | 4 | 4 |
| fields/number/input_radius_bottom_left | FLOAT | 32 | 28 | 20 | 35 |
| fields/number/input_radius_bottom_right | FLOAT | 32 | 28 | 20 | 35 |
| fields/number/input_radius_top_left | FLOAT | 32 | 28 | 20 | 35 |
| fields/number/input_radius_top_right | FLOAT | 32 | 28 | 20 | 35 |
| fields/number/large_height | FLOAT | 180 | 158 | 150 | 190 |
| fields/number/large_width | FLOAT | 140 | 123 | 116 | 148 |
| fields/number/medium_square | FLOAT | 128 | 113 | 80 | 136 |
| fields/text/medium/border | FLOAT | 3 | 3 | 3 | 3 |
| fields/text/medium/gap_horizontal | FLOAT | 16 | 14 | 14 | 17 |
| fields/text/medium/height | FLOAT | 100 | 88 | 84 | 106 |
| fields/text/medium/help_text_gap_vertical | FLOAT | 16 | 14 | 14 | 17 |
| fields/text/medium/padding_horizontal | FLOAT | 32 | 28 | 28 | 35 |
| fields/text/medium/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| fields/text/medium/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| fields/text/medium/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| fields/text/medium/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| fields/text/medium/suffix_icon_square | FLOAT | 48 | 42 | 40 | 51 |
| fields/text/small/border | FLOAT | 2 | 2 | 2 | 3 |
| fields/text/small/gap_horizontal | FLOAT | 12 | 11 | 10 | 13 |
| fields/text/small/height | FLOAT | 80 | 70 | 66 | 84 |
| fields/text/small/help_text_gap_vertical | FLOAT | 12 | 11 | 10 | 13 |
| fields/text/small/padding_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| fields/text/small/suffix_icon_square | FLOAT | 40 | 35 | 33 | 42 |
| gnb/temperature/decimal_places_witdh | FLOAT | 38 | 33 | 32 | 36 |
| gnb/temperature/number_width | FLOAT | 75 | 69 | 58 | 76 |
| imagery/profile/circle | FLOAT | 80 ← square/level18 | 70.4 | 66 ← square/level18 | 80 ← square/level18 |
| imagery/profile/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| imagery/profile/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| imagery/profile/radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| imagery/profile/radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| keyboard/keyword/enter_width | FLOAT | 200 | 176 | 166 | 212 |
| keyboard/keyword/height | FLOAT | 80 | 70 | 90 | 102 |
| keyboard/keyword/icon_square | FLOAT | 44 | 39 | 37 | 49 |
| keyboard/keyword/normal_width | FLOAT | 100 | 88 | 96 | 106 |
| keyboard/keyword/radius_bottom_left | FLOAT | 20 | 18 | 17 | 22 |
| keyboard/keyword/radius_bottom_right | FLOAT | 20 | 18 | 17 | 22 |
| keyboard/keyword/radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| keyboard/keyword/radius_top_right | FLOAT | 20 | 18 | 17 | 22 |
| keyboard/keyword/shift_width | FLOAT | 165 | 145 | 136 | 174 |
| keyboard/keyword/special_width | FLOAT | 120 | 106 | 100 | 128 |
| notification/border | FLOAT | 1 | 1 | 1 | 1 |
| notification/gap_large | FLOAT | 24 | 21 | 20 | 26 |
| notification/gap_medium | FLOAT | 16 | 14 | 14 | 17 |
| notification/padding_large | FLOAT | 32 | 28 | 28 | 35 |
| notification/padding_medium | FLOAT | 24 | 21 | 20 | 26 |
| notification/padding_small | FLOAT | 16 | 14 | 14 | 16 |
| notification/system/imagary_square | FLOAT | 100 | 88 | 83 | 106 |
| notification/system/images/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| notification/system/images/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| notification/system/images/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| notification/system/images/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| notification/system/profile/app_radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| notification/system/profile/app_radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| notification/system/profile/app_radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| notification/system/profile/app_radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| notification/system/profile/app_square | FLOAT | 48 | 42 | 40 | 51 |
| notification/system/profile/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| notification/system/profile/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| notification/system/profile/radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| notification/system/profile/radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| notification/system/radius_bottom_left | FLOAT | 48 | 42 | 40 | 51 |
| notification/system/radius_bottom_right | FLOAT | 48 | 42 | 40 | 51 |
| notification/system/radius_top_left | FLOAT | 48 | 42 | 40 | 51 |
| notification/system/radius_top_right | FLOAT | 48 | 42 | 40 | 51 |
| notification/system/text_gap_vertical | FLOAT | 12 | 11 | 10 | 13 |
| notification/system/text_padding_horizontal | FLOAT | 8 | 7 | 8 | 10 |
| notification/system/text_padding_top | FLOAT | 4 | 3 | 4 | 5 |
| notification/system/width | FLOAT | 700 | 616 | 582 | 745 |
| os_notification/container/width | FLOAT | 700 | 700 | 700 | 700 |
| os_notification/contents/image_square | FLOAT | 100 ← square/level22 | 88 ← square/level22 | 83 ← square/level22 | 100 ← square/level22 |
| os_notification/contents/image_thumb_radius | FLOAT | 16 ← radius/top_left/level5 | 14 ← radius/top_left/level5 | 13 ← radius/top_left/level5 | 16 ← radius/top_left/level5 |
| page_navigation/border_bottom | FLOAT | 1 | 1 | 1 | 1 |
| page_navigation/container_height | FLOAT | 80 | 70 | 66 | 84 |
| page_navigation/divider_height | FLOAT | 32 | 28 | 28 | 35 |
| page_navigation/divider_width | FLOAT | 2 | 2 | 2 | 3 |
| page_navigation/gap_extrasmall | FLOAT | 8 | 7 | 8 | 9 |
| page_navigation/gap_large | FLOAT | 24 | 21 | 20 | 26 |
| page_navigation/gap_medium | FLOAT | 16 | 14 | 14 | 17 |
| page_navigation/gap_samll | FLOAT | 12 | 11 | 10 | 13 |
| page_navigation/padding_extralarge | FLOAT | 40 | 35 | 32 | 42 |
| page_navigation/padding_large | FLOAT | 24 | 21 | 22 | 20 |
| page_navigation/padding_medium | FLOAT | 16 | 14 | 14 | 17 |
| page_navigation/padding_small | FLOAT | 8 | 7 | 8 | 9 |
| page_navigation/sub_title_gap_vertical | FLOAT | 4 | 4 | 4 | 4 |
| page_navigation/title/app_icon/container_square | FLOAT | 80 | 70 | 66 | 84 |
| page_navigation/title/app_icon/radius_botttom_left | FLOAT | 20 | 18 | 17 | 22 |
| page_navigation/title/app_icon/radius_botttom_right | FLOAT | 20 | 18 | 17 | 22 |
| page_navigation/title/app_icon/radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| page_navigation/title/app_icon/radius_top_right | FLOAT | 20 | 18 | 17 | 22 |
| page_navigation/title/depth_gap_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| page_navigation/title/depth_icon_square | FLOAT | 56 | 49 | 46 | 59 |
| page_navigation/title/dropdown_icon_square | FLOAT | 28 | 25 | 23 | 29 |
| page_navigation/title/dropdown_menu_pressed/radius_botttom_left | FLOAT | 16 | 14 | 13 | 17 |
| page_navigation/title/dropdown_menu_pressed/radius_botttom_right | FLOAT | 16 | 14 | 13 | 17 |
| page_navigation/title/dropdown_menu_pressed/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| page_navigation/title/dropdown_menu_pressed/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| page_navigation/title/group_gap_horizontal | FLOAT | 48 | 42 | 32 | 51 |
| popup/system/border | FLOAT | 2 | 2 | 2 | 3 |
| popup/system/close_icon_square | FLOAT | 48 | 42 | 40 | 51 |
| popup/system/gap_extralarge | FLOAT | 40 | 35 | 33 | 42 |
| popup/system/gap_extrasmall | FLOAT | 4 | 4 | 3 | 4 |
| popup/system/gap_large | FLOAT | 24 | 21 | 24 | 24 |
| popup/system/header_padding_horizontal | FLOAT | 32 | 28 | 27 | 35 |
| popup/system/icon_square | FLOAT | 80 | 70 | 66 | 84 |
| popup/system/large_width | FLOAT | 1200 | 1056 | 1000 | 1275 |
| popup/system/list/item/icon_square | FLOAT | 80 ← square/level18 | 70 ← square/level18 | 66 ← square/level18 | 80 ← square/level18 |
| popup/system/max_height | FLOAT | 1152 | 827 | 770 | 810 |
| popup/system/max_height_extended | FLOAT | 0 | 0 | 1240 | 1240 |
| popup/system/medium_width | FLOAT | 808 | 614 | 720 | 860 |
| popup/system/padding_large | FLOAT | 48 | 35 | 40 | 40 |
| popup/system/padding_small | FLOAT | 16 | 14 | 12 | 12 |
| popup/system/radius_bottom_left | FLOAT | 48 | 42 | 40 | 51 |
| popup/system/radius_bottom_right | FLOAT | 48 | 42 | 40 | 51 |
| popup/system/radius_top_left | FLOAT | 48 | 42 | 40 | 51 |
| popup/system/radius_top_right | FLOAT | 48 | 42 | 40 | 51 |
| popup/toast/icon | FLOAT | 48 | 42 | 40 | 51 |
| popup/toast/margin_bottom | FLOAT | 24 | 21 | 20 | 26 |
| popup/toast/margin_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| popup/toast/max_width | FLOAT | 792 | 696 | 760 | 840 |
| popup/toast/padding_horizontal | FLOAT | 40 | 35 | 34 | 42 |
| popup/toast/padding_vertical | FLOAT | 24 | 21 | 20 | 26 |
| popup/toast/radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| popup/toast/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| popup/toast/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| popup/toast/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| scroll/gap | FLOAT | 16 | 14 | 13 | 17 |
| scroll/padding_left | FLOAT | 12 | 11 | 10 | 13 |
| scroll/padding_right | FLOAT | 20 | 18 | 17 | 22 |
| scroll/padding_vertical | FLOAT | 16 | 14 | 13 | 17 |
| selections/border_large | FLOAT | 12 | 11 | 10 | 13 |
| selections/border_small | FLOAT | 4 | 4 | 3 | 4 |
| selections/checkbox/normal_button_border | FLOAT | 4 ← line/level4 | 4 | 3 ← line/level4 | 4 ← line/level4 |
| selections/checkbox/radius_bottom_left | FLOAT | 12 | 11 | 10 | 13 |
| selections/checkbox/radius_bottom_right | FLOAT | 12 | 11 | 10 | 13 |
| selections/checkbox/radius_top_left | FLOAT | 12 | 11 | 10 | 13 |
| selections/checkbox/radius_top_right | FLOAT | 12 | 11 | 10 | 13 |
| selections/container_large | FLOAT | 64 | 56 | 54 | 68 |
| selections/container_medium | FLOAT | 48 | 42 | 40 | 51 |
| selections/switch/medium/knob_radius_bottom_left | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/medium/knob_radius_bottom_right | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/medium/knob_radius_top_left | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/medium/knob_radius_top_right | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/medium/knob_square | FLOAT | 32 | 28 | 27 | 35 |
| selections/switch/medium/padding | FLOAT | 8 | 7 | 8 | 9 |
| selections/switch/medium/radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| selections/switch/medium/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| selections/switch/medium/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| selections/switch/medium/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| selections/switch/medium/width | FLOAT | 80 | 70 | 70 | 84 |
| selections/switch/small/knob_radius_bottom_left | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/small/knob_radius_bottom_right | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/small/knob_radius_top_left | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/small/knob_radius_top_right | FLOAT | 8 | 7 | 7 | 9 |
| selections/switch/small/knob_square | FLOAT | 28 | 25 | 23 | 29 |
| selections/switch/small/padding | FLOAT | 6 | 5 | 6 | 6 |
| selections/switch/small/radius_bottom_left | FLOAT | 12 | 11 | 10 | 13 |
| selections/switch/small/radius_bottom_right | FLOAT | 12 | 11 | 10 | 13 |
| selections/switch/small/radius_top_left | FLOAT | 12 | 11 | 10 | 13 |
| selections/switch/small/radius_top_right | FLOAT | 12 | 11 | 10 | 13 |
| selections/switch/small/width | FLOAT | 64 | 56 | 56 | 68 |
| spinner/focus_radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| spinner/focus_radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| spinner/focus_radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| spinner/focus_radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| spinner/gap_large | FLOAT | 24 | 28 | 28 | 35 |
| spinner/gap_medium | FLOAT | 20 | 18 | 18 | 22 |
| spinner/gap_small | FLOAT | 16 | 14 | 14 | 17 |
| spinner/list_height | FLOAT | 88 | 77 | 74 | 93 |
| spinner/list_mask_height | FLOAT | 60 | 53 | 50 | 64 |
| spinner/list_medium_height | FLOAT | 96 | 84 | 80 | 102 |
| spinner/number_height | FLOAT | 88 | 77 | 74 | 93 |
| spinner/number_width_large | FLOAT | 200 | 176 | 166 | 212 |
| spinner/number_width_medium | FLOAT | 140 | 123 | 116 | 148 |
| spinner/number_width_small | FLOAT | 110 | 97 | 92 | 116 |
| spinner/panel/large_height | FLOAT | 384 | 327 | 322 | 407 |
| spinner/panel/medium_height | FLOAT | 288 | 245 | 234 | 297 |
| spinner/title_top_padding | FLOAT | 16 | 14 | 14 | 17 |
| spinner/wheel/date_min_width | FLOAT | 540 | 475 | 448 | 574 |
| spinner/wheel/padding_horizontal | FLOAT | 16 | 14 | 14 | 17 |
| spinner/wheel/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| spinner/wheel/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| spinner/wheel/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| spinner/wheel/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| spinner/wheel/time_min_width | FLOAT | 480 | 422 | 398 | 509 |
| tabs/line/border_bottom | FLOAT | 1 | 1 | 1 | 1 |
| tabs/line/button/gap_horizontal | FLOAT | 8 | 7 | 8 | 9 |
| tabs/line/button/padding_horizontal | FLOAT | 14 | 12 | 12 | 15 |
| tabs/line/button/padding_vertical | FLOAT | 18 | 16 | 16 | 19 |
| tabs/line/button/prefix_square | FLOAT | 40 | 35 | 33 | 42 |
| tabs/line/button/pressed_radius_bottom_left | FLOAT | 12 | 11 | 10 | 13 |
| tabs/line/button/pressed_radius_bottom_right | FLOAT | 12 | 11 | 10 | 13 |
| tabs/line/button/pressed_radius_top_left | FLOAT | 12 | 11 | 10 | 13 |
| tabs/line/button/pressed_radius_top_right | FLOAT | 12 | 11 | 10 | 13 |
| tabs/line/button/wrap_padding_vertical | FLOAT | 12 | 11 | 10 | 13 |
| tabs/line/button/wrap_padding_vertical_large | FLOAT | 26 | 22 | 22 | 22 |
| tabs/line/gap_horizontal | FLOAT | 24 | 21 | 12 | 26 |
| tabs/line/padding_horizontal | FLOAT | 28 | 25 | 24 | 29 |
| tabs/line/selected_border_bottom | FLOAT | 4 | 4 | 3 | 4 |
| tabs/solid/large/button/gap_horizontal | FLOAT | 14 | 12 | 12 | 16 |
| tabs/solid/large/button/height | FLOAT | 84 | 74 | 70 | 90 |
| tabs/solid/large/button/padding_horizontal | FLOAT | 28 | 25 | 24 | 29 |
| tabs/solid/large/button/padding_vertical | FLOAT | 16 | 14 | 14 | 17 |
| tabs/solid/large/button/prefix_icon_square | FLOAT | 52 | 46 | 43 | 55 |
| tabs/solid/large/button/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/large/button/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/large/button/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/large/button/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/large/button/width | FLOAT | 200 | 176 | 166 | 212 |
| tabs/solid/large/height | FLOAT | 100 | 88 | 84 | 106 |
| tabs/solid/large/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| tabs/solid/large/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| tabs/solid/large/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| tabs/solid/large/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| tabs/solid/large/three_segments_width | FLOAT | 880 | 774 | 760 | 934 |
| tabs/solid/large/two_segments_width | FLOAT | 880 | 774 | 760 | 934 |
| tabs/solid/medium/button/gap_horizontal | FLOAT | 8 | 7 | 8 | 9 |
| tabs/solid/medium/button/height | FLOAT | 64 | 56 | 54 | 68 |
| tabs/solid/medium/button/padding_horizontal | FLOAT | 18 | 16 | 16 | 19 |
| tabs/solid/medium/button/padding_vertical | FLOAT | 12 | 11 | 10 | 13 |
| tabs/solid/medium/button/prefix_square | FLOAT | 40 | 35 | 33 | 42 |
| tabs/solid/medium/button/radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| tabs/solid/medium/button/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| tabs/solid/medium/button/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| tabs/solid/medium/button/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
| tabs/solid/medium/button/width | FLOAT | 160 | 141 | 134 | 170 |
| tabs/solid/medium/four_segments_width | FLOAT | 960 | 845 | 800 | 1020 |
| tabs/solid/medium/height | FLOAT | 80 | 70 | 66 | 84 |
| tabs/solid/medium/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/medium/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/medium/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/medium/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| tabs/solid/medium/three_segments_width | FLOAT | 780 | 686 | 670 | 828 |
| tabs/solid/medium/two_segments_width | FLOAT | 780 | 686 | 670 | 828 |
| tabs/solid/padding | FLOAT | 8 | 7 | 8 | 9 |
| vehicle_settings/list/item/container/padding_horizontal | FLOAT | 20 | 18 | 17 | 22 |
| vehicle_settings/list/item/container/padding_vertical | FLOAT | 16 | 14 | 13 | 17 |
| vehicle_settings/list/item/container/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| vehicle_settings/list/item/container/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| vehicle_settings/list/item/container/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| vehicle_settings/list/item/container/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| vehicle_settings/list/item/height | FLOAT | 112 | 99 | 93 | 119 |
| vehicle_settings/list/item/icon_container_square | FLOAT | 80 | 70 | 66 | 84 |
| vehicle_settings/list/padding_horizontal | FLOAT | 20 | 18 | 20 | 20 |
| vehicle_settings/list/padding_vertical | FLOAT | 24 | 21 | 24 | 24 |
| vehicle_settings/list/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| vehicle_settings/list/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| vehicle_settings/list/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| vehicle_settings/list/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| vehicle_settings/list/scroll_type_max_height | FLOAT | 528 | 465 | 324 | 560 |

### typo ⟨fcaac1⟩ — 50개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| line_height_sp/level1 | FLOAT | 24 | 21 | 20 | 26 |
| line_height_sp/level11 | FLOAT | 48 | 42 | 40 | 51 |
| line_height_sp/level15 | FLOAT | 64 | 55 | 53 | 68 |
| line_height_sp/level16 | FLOAT | 68 | 60 | 56 | 72 |
| line_height_sp/level17 | FLOAT | 72 | 65 | 60 | 77 |
| line_height_sp/level19 | FLOAT | 80 | 65 | 66 | 84 |
| line_height_sp/level20 | FLOAT | 134 | 118 | 156 | 142 |
| line_height_sp/level3 | FLOAT | 28 | 25 | 23 | 29 |
| line_height_sp/level6 | FLOAT | 34 | 28 | 28 | 36 |
| line_height_sp/level7 | FLOAT | 36 | 32 | 30 | 38 |
| line_height_sp/level8 | FLOAT | 38 | 33 | 32 | 41 |
| line_height_sp/level9 | FLOAT | 40 | 35 | 33 | 42 |
| line/level1 | FLOAT | 1 | 1 | 1 | 1 |
| line/level4 | FLOAT | 4 | 4 | 3 | 4 |
| radius/bottom_left/level4 | FLOAT | 12 | 11 | 10 | 13 |
| radius/bottom_left/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/bottom_right/level4 | FLOAT | 12 | 11 | 10 | 13 |
| radius/bottom_right/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/top_left/level4 | FLOAT | 12 | 11 | 10 | 13 |
| radius/top_left/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/top_right/level4 | FLOAT | 12 | 11 | 10 | 13 |
| radius/top_right/level5 | FLOAT | 16 | 14 | 13 | 17 |
| shadow/level1/blur | FLOAT | 24 | 21 | 20 | 26 |
| shadow/level1/y | FLOAT | 6 | 5 | 5 | 6 |
| shadow/level2/blur1 | FLOAT | 32 | 28 | 27 | 32 |
| shadow/level2/blur2 | FLOAT | 2 | 2 | 2 | 3 |
| shadow/level2/y1 | FLOAT | 8 | 7 | 7 | 9 |
| shadow/level2/y2 | FLOAT | 2 | 2 | 2 | 3 |
| shadow/level3/blur1 | FLOAT | 40 | 35 | 33 | 42 |
| shadow/level3/blur2 | FLOAT | 4 | 4 | 3 | 4 |
| shadow/level3/y1 | FLOAT | 20 | 18 | 17 | 22 |
| shadow/level3/y2 | FLOAT | 2 | 2 | 2 | 3 |
| size_sp/level11 | FLOAT | 36 | 32 | 30 | 38 |
| size_sp/level13 | FLOAT | 40 | 35 | 33 | 42 |
| size_sp/level14 | FLOAT | 44 | 39 | 37 | 47 |
| size_sp/level15 | FLOAT | 48 | 42 | 40 | 51 |
| size_sp/level16 | FLOAT | 52 | 46 | 43 | 55 |
| size_sp/level17 | FLOAT | 56 | 49 | 46 | 59 |
| size_sp/level18 | FLOAT | 60 | 53 | 50 | 64 |
| size_sp/level19 | FLOAT | 64 | 56 | 53 | 68 |
| size_sp/level20 | FLOAT | 112 | 99 | 130 | 119 |
| size_sp/level3 | FLOAT | 20 | 18 | 17 | 22 |
| size_sp/level5 | FLOAT | 24 | 21 | 20 | 26 |
| size_sp/level6 | FLOAT | 26 | 23 | 22 | 28 |
| size_sp/level7 | FLOAT | 28 | 25 | 23 | 29 |
| size_sp/level8 | FLOAT | 30 | 26 | 25 | 34 |
| size_sp/level9 | FLOAT | 32 | 28 | 27 | 35 |
| square/level18 | FLOAT | 80 | 70 | 66 | 84 |
| square/level22 | FLOAT | 100 | 88 | 83 | 106 |
| square/level8 | FLOAT | 40 | 35 | 33 | 42 |

### gleo.sys.color ⟨faa653⟩ — 1개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| gleo_blue | COLOR | #596BFF |

### Number ⟨60241c⟩ — 5개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| Common/20 | FLOAT | 20 |
| Radius/circle | FLOAT | 999 |
| Radius/l | FLOAT | 24 |
| Radius/xs | FLOAT | 0 |
| Spacing/0 | FLOAT | 0 |

### Number ⟨04bfd9⟩ — 1개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| Common/24 | FLOAT | 24 |

### theme ⟨652601⟩ — 2개, modes: Hyundai-Light / Hyundai-Dark / Kia-Light / Kia-Dark / Genesis-Light / Genesis-Dark

| name | type | Hyundai-Light | Hyundai-Dark | Kia-Light | Kia-Dark | Genesis-Light | Genesis-Dark |
|---|---|---|---|---|---|---|---|
| color/alpha/dark/200 | COLOR | #131417 α20% | #FFFFFF α20% | #161A1D α20% | #F5FBFF α20% | #131417 α20% | #FFFFFF α20% |
| color/divider/secondary | COLOR | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 | #161A1D α20% ← color/alpha/dark/200 | #F5FBFF α20% ← color/alpha/dark/200 | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 |

### Global ⟨5077c8⟩ — 1개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| Color/Neutral/60 | COLOR | #737373 |

## Text styles

| name | family | style | size | lineHeight | letterSpacing |
|---|---|---|---|---|---|
| Body/B2-Bold | 42dot Sans OTF | Bold | 24 | 32 PIXELS | 0 PERCENT |
| Body/B2-Regular | 42dot Sans OTF | Regular | 24 | 32 PIXELS | 0 PERCENT |
| Body/Large/Normal | Asta Sans | Regular | 30 | 38 PIXELS | 0 PERCENT |
| Body/Large/Strong | Asta Sans | Bold | 30 | 38 PIXELS | 0 PERCENT |
| Body/Medium/Strong | Asta Sans | Bold | 28 | 36 PIXELS | 0 PERCENT |
| Body/Small/Normal | Asta Sans | Regular | 26 | 34 PIXELS | 0 PERCENT |
| Body/Small/Strong | Asta Sans | Bold | 26 | 34 PIXELS | 0 PERCENT |
| Driving/Speed_kmh | Asta Sans | Bold | 112 | 134 PIXELS | -3 PERCENT |
| Driving/Traffic_Sign | Asta Sans | ExtraBold | 40 | 48 PIXELS | -3 PERCENT |
| Gleo AI/List_Number | Asta Sans | ExtraBold | 28 | 36 PIXELS | 0 PERCENT |
| Headline/Large/Extra_Strong | Asta Sans | ExtraBold | 60 | 80 PIXELS | 0 PERCENT |
| Headline/Large/Strong | Asta Sans | Bold | 60 | 80 PIXELS | 0 PERCENT |
| Headline/Medium/Extra_Strong | Asta Sans | ExtraBold | 56 | 72 PIXELS | 0 PERCENT |
| Headline/Small/Extra_Strong | Asta Sans | ExtraBold | 48 | 64 PIXELS | 0 PERCENT |
| Headline/Small/Strong | Asta Sans | Bold | 48 | 64 PIXELS | 0 PERCENT |
| Label/Medium | Asta Sans | Regular | 24 | 28 PIXELS | 0 PERCENT |
| Label/Small | Asta Sans | Regular | 20 | 24 PIXELS | 0 PERCENT |
| Paragraph/Medium | Manrope | Medium | 16 | 24 PIXELS | 0 PERCENT |
| Pleos/Body/XLarge/Regular | Asta Sans | Regular | 20 | 30 PIXELS | 0 PERCENT |
| Title/Large/Extra_Strong | Asta Sans | ExtraBold | 40 | 52 PIXELS | 0 PERCENT |
| Title/Large/Normal | Asta Sans | Regular | 40 | 52 PIXELS | 0 PERCENT |
| Title/Large/Strong | Asta Sans | Bold | 40 | 52 PIXELS | 0 PERCENT |
| Title/Medium/Extra_Strong | Asta Sans | ExtraBold | 36 | 44 PIXELS | 0 PERCENT |
| Title/Medium/Normal | Asta Sans | Regular | 36 | 44 PIXELS | 0 PERCENT |
| Title/Medium/Strong | Asta Sans | Bold | 36 | 44 PIXELS | 0 PERCENT |
| Title/Small/Strong | Asta Sans | Bold | 32 | 40 PIXELS | 0 PERCENT |
| Title/T2-Bold | 42dot Sans OTF | Bold | 32 | 40 PIXELS | 0 PERCENT |

## Effect styles

- **Shadow/Light/Level2**: DROP_SHADOW x0 y8 blur32 spread0 #000000 α16%; DROP_SHADOW x0 y2 blur2 spread0 #000000 α8%
- **App icon Lighting_Dark**: INNER_SHADOW x0 y-1 blur1 spread0 #FFFFFF α10%; INNER_SHADOW x0 y1 blur1 spread0 #FFFFFF α25%
- **Shadow/Light/Level2**: DROP_SHADOW x0 y8 blur32 spread0 #000000 α16%; DROP_SHADOW x0 y2 blur2 spread0 #000000 α8%
- **Shadow/Light/Level1**: DROP_SHADOW x0 y6 blur24 spread0 #000000 α8%
- **Shadow/Light/Level3**: DROP_SHADOW x0 y20 blur40 spread0 #000000 α24%; DROP_SHADOW x0 y2 blur4 spread0 #000000 α20%

## Fill styles

- **Secondary/Basic/Nagative**: #FF2E00
- **Secondary/Basic/Positive**: #00BA13
- **Secondary/Basic/Positive**: #00BA13
- **Core/Desaturated/Neutral950**: #252525
