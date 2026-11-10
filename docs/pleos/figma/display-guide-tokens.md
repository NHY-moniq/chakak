> 자동 추출본 — 출처: docs/Pleos_Connect_display_guide_215.fig (tools/tokens.mjs)
> 추출일: 2026-11-09. 값은 원본 그대로이며 앱 토큰으로 확정된 것이 아님. 요약은 ../design-foundations.md 참고.

# Display guide 2.1.5

## Variables

### Color Palette ⟨3b4c36⟩ — 139개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| _Feature/Driving/Normal/Alert | COLOR | #FF8A00 | #FF8A00 |
| _Feature/Driving/Normal/Danger | COLOR | #FF2E00 | #FF2E00 |
| _Feature/Driving/Normal/Positive | COLOR | #00BA13 | #00BA13 |
| _Feature/Driving/Normal/Warning | COLOR | #FFA133 | #FFA133 |
| _Legacy/Scale/Black/Black | COLOR | #000000 | #FFFFFF |
| _Legacy/Scale/White/300 | COLOR | #FFFFFF α16% | #151515 α12% |
| _Legacy/Scale/White/White | COLOR | #FFFFFF | #000000 |
| _Legacy/Solid/Cool/900 | COLOR | #1B1D21 | #F0F1F5 |
| alpha/dark/100 | COLOR | #131417 α10% | #FFFFFF α10% |
| alpha/dark/200 | COLOR | #131417 α20% | #FFFFFF α20% |
| alpha/dark/50 | COLOR | #131417 α5% | #FFFFFF α5% |
| basic/00-updated | COLOR | #FFFFFF | #131417 |
| basic/200 | COLOR | #E0E1E6 | #44464E |
| basic/900-updated | COLOR | #131417 | #FFFFFF |
| basic/alpha/dark_100 | COLOR | #131417 α10% | #FFFFFF α10% |
| basic/alpha/dark_200 | COLOR | #131417 α20% | #FFFFFF α20% |
| basic/alpha/dark_300 | COLOR | #131417 α30% | #FFFFFF α30% |
| basic/alpha/dark_50 | COLOR | #131417 α5% | #FFFFFF α5% |
| basic/static/dark_primary-updated | COLOR | #131417 | #131417 |
| basic/static/light_primary | COLOR | #FFFFFF | #FFFFFF |
| Brand/Teritary/Black | COLOR | #000000 ← _Legacy/Scale/Black/Black | #000000 ← _Legacy/Scale/White/White |
| Brand/Teritary/White | COLOR | #FFFFFF ← _Legacy/Scale/White/White | #FFFFFF ← _Legacy/Scale/Black/Black |
| color/alpha/dark/100 | COLOR | #131417 α10% | #FFFFFF α10% |
| color/alpha/dark/200 | COLOR | #131417 α20% | #FFFFFF α20% |
| color/alpha/dark/300 | COLOR | #131417 α30% | #FFFFFF α30% |
| color/alpha/dark/400 | COLOR | #131417 α40% | #FFFFFF α40% |
| color/alpha/dark/50 | COLOR | #131417 α5% | #FFFFFF α5% |
| color/alpha/light/100 | COLOR | #FFFFFF α10% | #131417 α10% |
| color/alpha/light/200 | COLOR | #FFFFFF α20% | #131417 α20% |
| color/alpha/light/500 | COLOR | #FFFFFF α50% | #131417 α50% |
| color/background/popup | COLOR | #FFFFFF ← basic/00-updated | #313236 ← color/basic/100 |
| color/basic/00 | COLOR | #FFFFFF | #131417 |
| color/basic/100 | COLOR | #EDEEF2 | #313236 |
| color/basic/50 | COLOR | #F7F8FA | #1D1E21 |
| color/basic/500 | COLOR | #686A72 | #C7C9CE |
| color/basic/600 | COLOR | #44464E | #E0E1E6 |
| color/basic/700 | COLOR | #313236 | #EDEEF2 |
| color/basic/900 | COLOR | #131417 | #FFFFFF |
| color/button/basic/disabled | COLOR | #FFFFFF α20% ← color/alpha/light/200 | #131417 α20% ← color/alpha/light/200 |
| color/button/basic/enabled | COLOR | #131417 α5% ← basic/alpha/dark_50 | #FFFFFF α5% ← basic/alpha/dark_50 |
| color/button/basic/pressed | COLOR | #131417 α10% ← basic/alpha/dark_100 | #FFFFFF α10% ← basic/alpha/dark_100 |
| color/button/filled/disabled | COLOR | #131417 α5% ← basic/alpha/dark_50 | #FFFFFF α5% ← basic/alpha/dark_50 |
| color/button/filled/enabled | COLOR | #44464E ← color/basic/600 | #E0E1E6 ← color/basic/600 |
| color/button/filled/pressed | COLOR | #313236 ← color/basic/700 | #C7C9CE ← color/basic/500 |
| color/dim/dark/primary | COLOR | #131417 α5% ← basic/alpha/dark_50 | #FFFFFF α5% ← basic/alpha/dark_50 |
| color/dim/dark/secondary | COLOR | #131417 α10% ← basic/alpha/dark_100 | #FFFFFF α10% ← basic/alpha/dark_100 |
| color/dim/dark/tertiary | COLOR | #131417 α20% ← basic/alpha/dark_200 | #FFFFFF α20% ← basic/alpha/dark_200 |
| color/dim/light/primary | COLOR | #FFFFFF α50% ← color/alpha/light/500 | #131417 α50% ← color/alpha/light/500 |
| color/divider/primary | COLOR | #131417 α5% ← basic/alpha/dark_50 | #FFFFFF α5% ← basic/alpha/dark_50 |
| color/divider/secondary | COLOR | #131417 α10% ← basic/alpha/dark_100 | #FFFFFF α10% ← basic/alpha/dark_100 |
| color/driving_view/charging_primary | COLOR | #00DB25 | #00DB25 |
| color/driving_view/gradient_end | COLOR | #FFFFFF ← basic/00-updated | #131417 ← basic/00-updated |
| color/driving_view/gradient_start-updated | COLOR | #FFFFFF α0% | #131417 α0% |
| color/icon/dark/primary | COLOR | #131417 ← basic/static/dark_primary-updated | #FFFFFF ← basic/static/light_primary |
| color/icon/dark/quaternary | COLOR | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 |
| color/icon/dark/secondary | COLOR | #131417 α84% ← color/static/dark/200 | #FFFFFF α84% ← color/static/light/200 |
| color/icon/dark/tertiary | COLOR | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 |
| color/icon/light/primary | COLOR | #FFFFFF ← basic/static/light_primary | #131417 ← basic/static/dark_primary-updated |
| color/informative/active | COLOR | #02C265 | #00E074 |
| color/informative/autonomous_driving | COLOR | #5A46FA | #6A57FF |
| color/regulation/blue | COLOR | #0064FF | #0082FF |
| color/regulation/green | COLOR | #00BA13 | #55F165 |
| color/regulation/orange | COLOR | #FF8A00 | #FF8A00 |
| color/regulation/red | COLOR | #F62E24 | #FF4339 |
| color/regulation/yellow | COLOR | #FFC224 | #FFC224 |
| color/static/dark/100 | COLOR | #131417 | #131417 |
| color/static/dark/200 | COLOR | #131417 α84% | #131417 α84% |
| color/static/dark/300 | COLOR | #131417 α64% | #131417 α64% |
| color/static/dark/400 | COLOR | #131417 α32% | #131417 α32% |
| color/static/light/100 | COLOR | #FFFFFF | #FFFFFF |
| color/static/light/200 | COLOR | #FFFFFF α84% | #FFFFFF α84% |
| color/static/light/300 | COLOR | #FFFFFF α64% | #FFFFFF α64% |
| color/static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% |
| color/surface/basic | COLOR | #FFFFFF ← basic/00-updated | #1D1E21 ← color/basic/50 |
| color/switch/knob_on | COLOR | #FFFFFF ← basic/00-updated | #FFFFFF ← color/basic/900 |
| color/switch/off | COLOR | #131417 α30% ← basic/alpha/dark_300 | #FFFFFF α10% ← basic/alpha/dark_100 |
| color/switch/on | COLOR | #02C265 ← system/informative/active | #00E074 ← system/informative/active |
| color/text/dark/primary | COLOR | #131417 ← basic/static/dark_primary-updated | #FFFFFF ← basic/static/light_primary |
| color/text/dark/quaternary | COLOR | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 |
| color/text/dark/secondary | COLOR | #131417 α84% ← color/static/dark/200 | #FFFFFF α84% ← color/static/light/200 |
| color/text/dark/tertiary | COLOR | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 |
| color/text/light/primary | COLOR | #FFFFFF ← basic/static/light_primary | #131417 ← basic/static/dark_primary-updated |
| color/text/light/secondary | COLOR | #FFFFFF α84% ← color/static/light/200 | #131417 α84% ← color/static/dark/200 |
| color/text/light/tertiary | COLOR | #FFFFFF α64% ← color/static/light/300 | #131417 α64% ← color/static/dark/300 |
| dim/dark/secondary | COLOR | #131417 α10% ← basic/alpha/dark_100 | #FFFFFF α10% ← basic/alpha/dark_100 |
| dim/dark/tertiary | COLOR | #131417 α20% ← basic/alpha/dark_200 | #FFFFFF α20% ← basic/alpha/dark_200 |
| feature/driving_view/gradient_end-updated | COLOR | #FFFFFF ← basic/00-updated | #131417 ← basic/00-updated |
| feature/driving_view/gradient_start-updated | COLOR | #FFFFFF α0% | #131417 α0% |
| fixed/dark/100 | COLOR | #131417 | #131417 |
| fixed/dark/400 | COLOR | #131417 α32% | #131417 α32% |
| fixed/light/100 | COLOR | #FFFFFF | #FFFFFF |
| fixed/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% |
| icon/dark/primary | COLOR | #131417 ← basic/static/dark_primary-updated | #FFFFFF ← basic/static/light_primary |
| icon/dark/tertiary | COLOR | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 |
| icon/light/primary | COLOR | #FFFFFF ← basic/static/light_primary | #131417 ← basic/static/dark_primary-updated |
| informative/active | COLOR | #02C265 | #00E074 |
| informative/positive | COLOR | #0064FF | #0082FF |
| phone/accept_normal | COLOR | #32B957 | #3BD665 |
| phone/accept_pressed | COLOR | #279E47 | #2DB752 |
| phone/end_normal | COLOR | #FE3D16 | #FF4C28 |
| phone/end_pressed | COLOR | #E73612 | #DF4323 |
| static/dark/100 | COLOR | #131417 | #131417 |
| static/dark/200 | COLOR | #131417 α84% | #131417 α84% |
| static/dark/300 | COLOR | #131417 α64% | #131417 α64% |
| static/light/100 | COLOR | #FFFFFF | #FFFFFF |
| static/light/300 | COLOR | #FFFFFF α64% | #FFFFFF α64% |
| static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% |
| surface/accent | COLOR | #E0E1E6 ← basic/200 | #44464E ← basic/200 |
| system/dim/gray_primary | COLOR | #131417 α5% ← basic/alpha/dark_50 | #FFFFFF α5% ← basic/alpha/dark_50 |
| system/dim/gray_tertiary | COLOR | #131417 α20% ← basic/alpha/dark_200 | #FFFFFF α20% ← basic/alpha/dark_200 |
| System/Grayscale/Opaque/Black | COLOR | #000000 | #000000 |
| System/Grayscale/Opaque/White | COLOR | #FFFFFF | #FFFFFF |
| system/icon/gray_primary | COLOR | #131417 ← color/basic/900 | #FFFFFF ← color/basic/900 |
| system/icon/gray_quaternary | COLOR | #131417 α32% | #FFFFFF α32% |
| system/icon/gray_tertiary | COLOR | #131417 α64% | #FFFFFF α64% |
| system/icon/white_primary | COLOR | #FFFFFF ← basic/00-updated | #131417 ← basic/00-updated |
| system/informative/active | COLOR | #02C265 | #00E074 |
| system/informative/autonomous_driving | COLOR | #5A46FA | #6A57FF |
| system/label/gray_primary | COLOR | #131417 ← color/basic/900 | #FFFFFF ← color/basic/900 |
| system/label/gray_quaternary | COLOR | #1B1C1E α32% | #FFFFFF α32% |
| system/label/gray_secondary | COLOR | #1B1C1E α84% | #FFFFFF α84% |
| system/label/gray_tertiary | COLOR | #1B1C1E α64% | #FFFFFF α64% |
| system/regulation/blue | COLOR | #0064FF | #0082FF |
| system/regulation/green | COLOR | #00BA13 | #55F165 |
| system/regulation/orange | COLOR | #FF8A00 | #FF8A00 |
| system/regulation/red | COLOR | #F62E24 | #FF4339 |
| system/regulation/yellow | COLOR | #FFC224 | #FFC224 |
| system/switch/background_off | COLOR | #131417 α30% ← basic/alpha/dark_300 | #FFFFFF α10% ← basic/alpha/dark_100 |
| system/switch/background_on | COLOR | #02C265 ← system/informative/active | #00E074 ← system/informative/active |
| system/switch/button_on | COLOR | #FFFFFF ← basic/00-updated | #FFFFFF ← color/basic/900 |
| text/dark/primary | COLOR | #131417 ← basic/static/dark_primary-updated | #FFFFFF ← basic/static/light_primary |
| text/dark/quaternary | COLOR | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 |
| text/dark/tertiary | COLOR | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 |
| White | COLOR | #FFFFFF | #FFFFFF |

### Number ⟨04bfd9⟩ — 10개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| Common/12 | FLOAT | 12 |
| Common/16 | FLOAT | 16 |
| Common/24 | FLOAT | 24 |
| Common/32 | FLOAT | 32 |
| Common/4 | FLOAT | 4 |
| Common/40 | FLOAT | 40 |
| Common/48 | FLOAT | 48 |
| Common/8 | FLOAT | 8 |
| Radius/m | FLOAT | 16 |
| Spacing/0 | FLOAT | 0 |

### Color ⟨8d369c⟩ — 9개, modes: Light / Dark / Pleos

| name | type | Light | Dark | Pleos |
|---|---|---|---|---|
| Content/Default/Normal | COLOR | #0F172A ← Core/Desaturate/Neutral90 | #FFFFFF ← Core/White | #0F172A ← Core/Desaturate/Neutral90 |
| Core/Desaturate/Neutral20 | COLOR | #DDE1EB | #DDE1EB | #DDE1EB |
| Core/Desaturate/Neutral50 | COLOR | #8793AD | #8793AD | #8793AD |
| Core/Desaturate/Neutral60 | COLOR | #606D8A | #606D8A | #606D8A |
| Core/Desaturate/Neutral75 | COLOR | #2F3B57 | #2F3B57 | #2F3B57 |
| Core/Desaturate/Neutral90 | COLOR | #0F172A | #0F172A | #0F172A |
| Core/White | COLOR | #FFFFFF | #FFFFFF | #FFFFFF |
| Line/Divider/Regular | COLOR | #DDE1EB ← Core/Desaturate/Neutral20 | #2F3B57 ← Core/Desaturate/Neutral75 | #DDE1EB ← Core/Desaturate/Neutral20 |
| Surface/Status/Accent/Idle | COLOR | #606D8A ← Core/Desaturate/Neutral60 | #8793AD ← Core/Desaturate/Neutral50 | #606D8A ← Core/Desaturate/Neutral60 |

### Color ⟨815ca0⟩ — 3개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| Content/Default/Inverse/Normal | COLOR | #FFFFFF ← Core/White | #0F172A ← Core/Desaturate/Neutral90 |
| Core/Desaturate/Neutral90 | COLOR | #0F172A | #0F172A |
| Core/White | COLOR | #FFFFFF | #FFFFFF |

### Color ⟨994235⟩ — 64개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| _Feature/Driving/Normal/Information | COLOR | #2E5CFF | #2E5CFF |
| _Legacy/Scale/Black/400 | COLOR | #151515 α22% | #FFFFFF α22% |
| _Legacy/Scale/Black/Black | COLOR | #000000 | #FFFFFF |
| _Legacy/Scale/White/White | COLOR | #FFFFFF | #000000 |
| _Legacy/Solid/Cool/500 | COLOR | #626675 | #626675 |
| _Legacy/Solid/Cool/900 | COLOR | #1B1D21 | #F0F1F5 |
| _Legacy/Solid/Warm/300 | COLOR | #C8C8C8 | #353535 |
| _System/Brand/Primary/Normal | COLOR | #5A46FA | #6A57FF |
| alpha/dark/100 | COLOR | #131417 α10% | #FFFFFF α10% |
| alpha/dark/200 | COLOR | #131417 α20% | #FFFFFF α20% |
| alpha/dark/300 | COLOR | #131417 α30% | #FFFFFF α30% |
| alpha/dark/50 | COLOR | #131417 α5% | #FFFFFF α5% |
| alpha/dark/500 | COLOR | #131417 α50% | #FFFFFF α50% |
| alpha/light/200 | COLOR | #FFFFFF α20% | #131417 α20% |
| background/popup | COLOR | #FFFFFF ← basic/00 | #313236 ← basic/100 |
| background/primary | COLOR | #F7F8FA ← basic/50 | #1D1E21 ← basic/50 |
| basic/00 | COLOR | #FFFFFF | #131417 |
| basic/100 | COLOR | #EDEEF2 | #313236 |
| basic/50 | COLOR | #F7F8FA | #1D1E21 |
| basic/500 | COLOR | #686A72 | #C7C9CE |
| basic/600 | COLOR | #44464E | #E0E1E6 |
| basic/700 | COLOR | #313236 | #EDEEF2 |
| basic/800 | COLOR | #1D1E21 | #F7F8FA |
| Brand/Teritary/Black | COLOR | #000000 ← _Legacy/Scale/Black/Black | #000000 ← _Legacy/Scale/White/White |
| button/basic/disabled | COLOR | #FFFFFF α20% ← alpha/light/200 | #131417 α20% ← alpha/light/200 |
| button/basic/enabled | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| button/basic/pressed | COLOR | #131417 α10% ← alpha/dark/100 | #FFFFFF α10% ← alpha/dark/100 |
| button/filled/disabled | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| button/filled/enabled | COLOR | #44464E ← basic/600 | #E0E1E6 ← basic/600 |
| button/filled/pressed | COLOR | #313236 ← basic/700 | #C7C9CE ← basic/500 |
| button/switch/enabled | COLOR | #FFFFFF ← basic/00 | #FFFFFF α20% ← alpha/dark/200 |
| Common/Empty/Content | COLOR | #C8C8C8 ← _Legacy/Solid/Warm/300 | #626675 ← _Legacy/Solid/Cool/500 |
| controller/slider/knop | COLOR | #FFFFFF ← basic/00 | #FFFFFF α30% ← alpha/dark/300 |
| controller/slider/normal | COLOR | #131417 α20% ← alpha/dark/200 | #FFFFFF α10% ← alpha/dark/100 |
| controller/slider/pressed | COLOR | #131417 α30% ← alpha/dark/300 | #FFFFFF α20% ← alpha/dark/200 |
| controller/tab_background | COLOR | #131417 α5% ← alpha/dark/50 | #FFFFFF α5% ← alpha/dark/50 |
| divider/secondary | COLOR | #131417 α10% ← alpha/dark/100 | #FFFFFF α10% ← alpha/dark/100 |
| divider/tertiary | COLOR | #131417 α20% ← alpha/dark/200 | #FFFFFF α20% ← alpha/dark/200 |
| icon/dark/primary | COLOR | #131417 ← static/dark/100 | #FFFFFF ← static/light/100 |
| icon/dark/quaternary | COLOR | #131417 α32% ← static/dark/400 | #FFFFFF α32% ← static/light/400 |
| icon/dark/tertiary | COLOR | #131417 α64% ← static/dark/300 | #FFFFFF α64% ← static/light/300 |
| icon/light/primary | COLOR | #FFFFFF ← static/light/100 | #131417 ← static/dark/100 |
| informative/active | COLOR | #02C265 | #00E074 |
| informative/negative | COLOR | #FE3D16 | #FF4C28 |
| informative/positive | COLOR | #0064FF | #0082FF |
| phone/accept_normal | COLOR | #32B957 | #3BD665 |
| phone/end_normal | COLOR | #FE3D16 | #FF4C28 |
| regulation/blue | COLOR | #0064FF | #0082FF |
| regulation/orange | COLOR | #FF8A00 | #FF8A00 |
| regulation/red | COLOR | #F62E24 | #FF4339 |
| static/dark/100 | COLOR | #131417 | #131417 |
| static/dark/200 | COLOR | #131417 α84% | #131417 α84% |
| static/dark/300 | COLOR | #131417 α64% | #131417 α64% |
| static/dark/400 | COLOR | #131417 α32% | #131417 α32% |
| static/light/100 | COLOR | #FFFFFF | #FFFFFF |
| static/light/200 | COLOR | #FFFFFF α84% | #FFFFFF α84% |
| static/light/300 | COLOR | #FFFFFF α64% | #FFFFFF α64% |
| static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% |
| system/divider/divider_tertiary-added | COLOR | #FFFFFF α20% ← alpha/light/200 | #131417 α20% ← alpha/light/200 |
| text/dark/primary | COLOR | #131417 ← static/dark/100 | #FFFFFF ← static/light/100 |
| text/dark/quaternary | COLOR | #131417 α32% ← static/dark/400 | #FFFFFF α32% ← static/light/400 |
| text/dark/secondary | COLOR | #131417 α84% ← static/dark/200 | #FFFFFF α84% ← static/light/200 |
| text/dark/tertiary | COLOR | #131417 α64% ← static/dark/300 | #FFFFFF α64% ← static/light/300 |
| text/light/primary | COLOR | #FFFFFF ← static/light/100 | #131417 ← static/dark/100 |

### typo ⟨fcaac1⟩ — 37개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| line_height_sp/level17 | FLOAT | 72 | 65 | 60 | 77 |
| line_height_sp/level19 | FLOAT | 80 | 65 | 66 | 84 |
| line_height_sp/level20 | FLOAT | 134 | 118 | 156 | 142 |
| line_height_sp/level6 | FLOAT | 34 | 28 | 28 | 36 |
| line_height_sp/level7 | FLOAT | 36 | 32 | 30 | 38 |
| line_height_sp/level8 | FLOAT | 38 | 33 | 32 | 41 |
| line_height_sp/level9 | FLOAT | 40 | 35 | 33 | 42 |
| line/level1 | FLOAT | 1 | 1 | 1 | 1 |
| radius/bottom_left/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/bottom_left/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/bottom_right/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/bottom_right/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/top_left/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/top_left/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/top_right/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/top_right/level7 | FLOAT | 24 | 21 | 20 | 26 |
| shadow/level1/blur | FLOAT | 24 | 21 | 20 | 26 |
| shadow/level1/y | FLOAT | 6 | 5 | 5 | 6 |
| shadow/level2/blur1 | FLOAT | 32 | 28 | 27 | 32 |
| shadow/level2/blur2 | FLOAT | 2 | 2 | 2 | 3 |
| shadow/level2/y1 | FLOAT | 8 | 7 | 7 | 9 |
| shadow/level2/y2 | FLOAT | 2 | 2 | 2 | 3 |
| size_sp/level11 | FLOAT | 36 | 32 | 30 | 38 |
| size_sp/level13 | FLOAT | 40 | 35 | 33 | 42 |
| size_sp/level14 | FLOAT | 44 | 39 | 37 | 47 |
| size_sp/level15 | FLOAT | 48 | 42 | 40 | 51 |
| size_sp/level16 | FLOAT | 52 | 46 | 43 | 55 |
| size_sp/level17 | FLOAT | 56 | 49 | 46 | 59 |
| size_sp/level18 | FLOAT | 60 | 53 | 50 | 64 |
| size_sp/level19 | FLOAT | 64 | 56 | 53 | 68 |
| size_sp/level20 | FLOAT | 112 | 99 | 130 | 119 |
| size_sp/level6 | FLOAT | 26 | 23 | 22 | 28 |
| size_sp/level7 | FLOAT | 28 | 25 | 23 | 29 |
| size_sp/level8 | FLOAT | 30 | 26 | 25 | 34 |
| size_sp/level9 | FLOAT | 32 | 28 | 27 | 35 |
| square/level10 | FLOAT | 48 | 42 | 40 | 51 |
| square/level18 | FLOAT | 80 | 70 | 66 | 84 |

### form_factor ⟨96b6cf⟩ — 249개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| bars/app_control/container_height | FLOAT | 60 | 53 | 100 | 128 |
| bars/app_control/container_width | FLOAT | 320 | 282 | 266 | 340 |
| bars/app_control/handle_height | FLOAT | 8 | 7 | 8 | 9 |
| bars/app_control/handle_width_normal | FLOAT | 140 | 123 | 116 | 148 |
| bars/app_control/padding_top | FLOAT | 16 | 14 | 14 | 17 |
| bars/fullscreen_control/icon_button_radius_top_left | FLOAT | 28 | 25 | 23 | 29 |
| bars/fullscreen_control/icon_padding_bottom | FLOAT | 14 | 12 | 12 | 15 |
| bars/fullscreen_control/icon_padding_horizontal | FLOAT | 14 | 12 | 12 | 15 |
| bars/fullscreen_control/icon_padding_top | FLOAT | 14 | 12 | 12 | 15 |
| bars/fullscreen_control/magnify_icon_square | FLOAT | 56 | 49 | 46 | 59 |
| bars/fullscreen_control/padding_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| bars/fullscreen_control/padding_top | FLOAT | 24 | 21 | 20 | 26 |
| bars/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| bars/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| bars/radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| bars/radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| bars/window_control/container_height | FLOAT | 200 | 176 | 240 | 308 |
| bars/window_control/container_width | FLOAT | 20 | 18 | 40 | 48 |
| bars/window_control/handle_height | FLOAT | 160 | 141 | 132 | 170 |
| bars/window_control/handle_height_pressed | FLOAT | 80 | 70 | 66 | 84 |
| bars/window_control/handle_width | FLOAT | 4 | 4 | 8 | 10 |
| bars/window_control/padding_horizontal | FLOAT | 8 | 7 | 16 | 20 |
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
| buttons/basic/medium/radius_top_left_medium | FLOAT | 24 | 21 | 20 | 26 |
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
| controller/slider/border_large | FLOAT | 4 | 4 | 3 | 4 |
| controller/slider/continuous/medium/knob/radius_bottom_left | FLOAT | 20 | 20 | 17 | 22 |
| controller/slider/continuous/medium/knob/radius_bottom_right | FLOAT | 20 | 20 | 17 | 22 |
| controller/slider/continuous/medium/knob/radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| controller/slider/continuous/medium/knob/radius_top_right | FLOAT | 20 | 20 | 17 | 22 |
| controller/slider/continuous/small/knob/radius_bottom_left | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/knob/radius_bottom_right | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/knob/radius_top_left | FLOAT | 16 | 14 | 13 | 17 |
| controller/slider/continuous/small/knob/radius_top_right | FLOAT | 16 | 14 | 13 | 17 |
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
| controller/slider/height_medium | FLOAT | 80 | 70 | 66 | 84 |
| controller/slider/height_small | FLOAT | 72 | 67 | 64 | 81 |
| driving_view(wip)/group1/indicator_padding_horizontal | FLOAT | 20 | 18 | 20 | 20 |
| driving_view/left/group1/padding_horizontal_left | FLOAT | 20 | 18 | 20 | 21 |
| driving_view/top/group1/prnd_unit_width | FLOAT | 30 | 26 | 30 | 32 |
| gnb/application/app_activate_height | FLOAT | 8 | 7 | 8 | 10 |
| gnb/application/app_activate_width | FLOAT | 8 | 7 | 8 | 10 |
| gnb/application/app_square | FLOAT | 84 | 74 | 70 | 90 |
| gnb/application/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| gnb/application/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| gnb/application/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| gnb/application/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| gnb/controller/arrow_icon_sqaure | FLOAT | 64 | 56 | 53 | 68 |
| gnb/controller/arrow_width | FLOAT | 80 | 70 | 108 | 138 |
| gnb/controller/divider | FLOAT | 4 | 4 | 3 | 4 |
| gnb/controller/divider_pressed_square | FLOAT | 64 | 56 | 52 | 67 |
| gnb/controller/icon_square | FLOAT | 72 | 63 | 60 | 77 |
| gnb/controller/pressed_square | FLOAT | 100 | 88 | 88 | 108 |
| gnb/controller/width | FLOAT | 720 | 548 | 0 | 0 |
| gnb/gap_large | FLOAT | 12 | 11 | 10 | 12 |
| gnb/gap_medium | FLOAT | 8 | 8 | 8 | 10 |
| gnb/gap_small | FLOAT | 4 | 4 | 4 | 5 |
| gnb/height | FLOAT | 160 | 141 | 0 | 0 |
| gnb/menu/container_square | FLOAT | 124 | 109 | 108 | 138 |
| gnb/menu/radius_bottom_left | FLOAT | 28 | 25 | 23 | 29 |
| gnb/menu/radius_bottom_right | FLOAT | 28 | 25 | 23 | 29 |
| gnb/menu/radius_top_left | FLOAT | 28 | 25 | 23 | 29 |
| gnb/menu/radius_top_right | FLOAT | 28 | 25 | 23 | 29 |
| gnb/padding_horizontal | FLOAT | 60 | 20 | 0 | 0 |
| gnb/remove_button/background_square | FLOAT | 40 | 35 | 36 | 44 |
| gnb/remove_button/container_square | FLOAT | 44 | 39 | 40 | 48 |
| gnb/remove_button/icon_height | FLOAT | 3 | 3 | 4 | 4 |
| gnb/remove_button/icon_width | FLOAT | 16 | 14 | 16 | 18 |
| gnb/temperature/container_width | FLOAT | 164 | 116 | 108 | 138 |
| gnb/temperature/decimal_places_padding_top | FLOAT | 16 | 14 | 14 | 14 |
| gnb/temperature/decimal_places_witdh | FLOAT | 38 | 33 | 32 | 36 |
| gnb/temperature/gap_horizontal | FLOAT | -7 | -4 | -8 | -8 |
| gnb/temperature/gap_vertical | FLOAT | -4 | -4 | 0 | -4 |
| gnb/temperature/icon_group_height | FLOAT | 40 | 35 | 30 | 42 |
| gnb/temperature/icon_square | FLOAT | 36 | 28 | 27 | 35 |
| gnb/temperature/number_width | FLOAT | 75 | 69 | 58 | 76 |
| gnb/temperature/off_gap_horizontal | FLOAT | 4 | 4 | 4 | 3 |
| gnb/temperature/wind_icon_square | FLOAT | 48 | 28 | 27 | 35 |
| imagery/profile/circle | FLOAT | 80 ← square/level18 | 70.4 | 66 ← square/level18 | 80 ← square/level18 |
| imagery/profile/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| imagery/profile/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| imagery/profile/radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| imagery/profile/radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| osd/border | FLOAT | 1 | 1 | 1 | 1 |
| osd/control/radius_bottom_left | FLOAT | 24 ← radius/bottom_left/level7 | 21 | 20 ← radius/bottom_left/level7 | 24 ← radius/bottom_left/level7 |
| osd/control/radius_bottom_right | FLOAT | 24 ← radius/bottom_right/level7 | 21 | 20 ← radius/bottom_right/level7 | 24 ← radius/bottom_right/level7 |
| osd/control/radius_top_left | FLOAT | 24 ← radius/top_left/level7 | 21 | 20 ← radius/top_left/level7 | 24 ← radius/top_left/level7 |
| osd/control/radius_top_right | FLOAT | 24 ← radius/top_right/level7 | 21 | 20 ← radius/top_right/level7 | 24 ← radius/top_right/level7 |
| osd/distance/image_gap_horizontal | FLOAT | 16 | 14 | 14 | 18 |
| osd/distance/image_width | FLOAT | 128 | 113 | 108 | 140 |
| osd/distance/level 1 | FLOAT | 80 | 70 | 66 | 84 |
| osd/distance/level 2 | FLOAT | 124 | 109 | 103 | 132 |
| osd/distance/level 3 | FLOAT | 186 | 164 | 154 | 197 |
| osd/distance/level 4 | FLOAT | 320 | 282 | 266 | 340 |
| osd/distance/text_container_square | FLOAT | 40 | 35 | 32 | 42 |
| osd/gap_horizontal_large | FLOAT | 20 | 14 | 16 | 22 |
| osd/gap_horizontal_medium | FLOAT | 16 | 14 | 14 | 18 |
| osd/gap_horizontal_small | FLOAT | 8 | 7 | 8 | 9 |
| osd/height | FLOAT | 120 | 106 | 120 | 120 |
| osd/icon_button_square | FLOAT | 80 ← square/level18 | 70 | 66 ← square/level18 | 80 ← square/level18 |
| osd/lights/button_width | FLOAT | 120 | 120 | 120 | 120 |
| osd/lights/title_prefix_icon_square | FLOAT | 48 ← square/level10 | 42 | 40 ← square/level10 | 48 ← square/level10 |
| osd/music/album_cover/radius_bottom_left | FLOAT | 24 | 21 | 20 | 26 |
| osd/music/album_cover/radius_bottom_right | FLOAT | 24 | 21 | 20 | 26 |
| osd/music/album_cover/radius_top_left | FLOAT | 24 | 21 | 20 | 26 |
| osd/music/album_cover/radius_top_right | FLOAT | 24 | 21 | 20 | 26 |
| osd/music/artist_name_min_width | FLOAT | 160 | 141 | 160 | 160 |
| osd/music/title_max_width | FLOAT | 328 | 289 | 328 | 328 |
| osd/padding_medium | FLOAT | 24 | 18 | 20 | 26 |
| osd/padding_small | FLOAT | 16 | 14 | 14 | 18 |
| osd/radius_bottom_left | FLOAT | 32 | 28 | 27 | 35 |
| osd/radius_bottom_right | FLOAT | 32 | 28 | 27 | 35 |
| osd/radius_top_left | FLOAT | 32 | 28 | 27 | 35 |
| osd/radius_top_right | FLOAT | 32 | 28 | 27 | 35 |
| osd/seat_position/title_prefix_icon_square | FLOAT | 48 ← square/level10 | 42 | 40 ← square/level10 | 48 ← square/level10 |
| osd/title/icon_square | FLOAT | 52 | 46 | 44 | 56 |
| osd/title/prefix_gap_horizontal | FLOAT | 12 | 11 | 10 | 13 |
| osd/title/prefix_icon_square | FLOAT | 48 | 42 | 40 | 51 |
| osd/title/width | FLOAT | 180 | 160 | 148 | 190 |
| osd/width | FLOAT | 880 | 774 | 730 | 934 |
| statusbar/gap_large | FLOAT | 24 | 21 | 16 | 20 |
| statusbar/gap_medium | FLOAT | 16 | 14 | 12 | 15 |
| statusbar/height | FLOAT | 96 | 84 | 0 | 0 |
| statusbar/menus/container_radius_bottom_left | FLOAT | 20 | 18 | 17 | 22 |
| statusbar/menus/container_radius_bottom_right | FLOAT | 20 | 18 | 17 | 22 |
| statusbar/menus/container_radius_top_left | FLOAT | 20 | 18 | 17 | 22 |
| statusbar/menus/container_radius_top_right | FLOAT | 20 | 18 | 17 | 22 |
| statusbar/menus/container_square | FLOAT | 80 | 70 | 66 | 84 |
| statusbar/menus/icon_square | FLOAT | 56 | 49 | 46 | 59 |
| statusbar/menus/privacy/dot | FLOAT | 10 | 9 | 8 | 10 |
| statusbar/menus/privacy/indicator_gap | FLOAT | 4 | 4 | 3 | 4 |
| statusbar/menus/privacy/indicator_height_large | FLOAT | 100 | 88 | 84 | 106 |
| statusbar/menus/privacy/indicator_height_small | FLOAT | 80 | 70 | 66 | 84 |
| statusbar/menus/privacy/indicator_icon_square | FLOAT | 32 | 28 | 26 | 35 |
| statusbar/menus/privacy/indicator_padding | FLOAT | 12 | 11 | 10 | 13 |
| statusbar/menus/privacy/indicator_width_large | FLOAT | 100 | 88 | 84 | 106 |
| statusbar/menus/privacy/indicator_width_small | FLOAT | 80 | 70 | 66 | 84 |
| statusbar/menus/privacy/padding | FLOAT | 8 | 7 | 7 | 9 |
| statusbar/menus/profile_square | FLOAT | 60 | 53 | 50 | 64 |
| statusbar/padding | FLOAT | 24 | 21 | 20 | 26 |
| statusbar/wide_time_padding_horizontal_right | FLOAT | 0 | 0 | 28 | 26 |
| statusbar/wide_time_padding_vertical_bottom | FLOAT | 0 | 0 | 20 | 26 |
| statusbar/wide_time_padding_vertical_top | FLOAT | 0 | 0 | 8 | 26 |
| statusbar/width | FLOAT | 0 | 0 | 168 | 128 |
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
| window/apps/application_height | FLOAT | 1184 | 855 | 810 | 802 |
| window/apps/driving_view_height | FLOAT | 1280 | 939 | 850 | 850 |
| window/apps/driving_view_width | FLOAT | 840 | 628 | 1040 | 1280 |
| window/apps/landscape_width | FLOAT | 1700 | 1274 | 1640 | 1480 |
| window/apps/padding | FLOAT | 20 | 18 | 20 | 24 |
| window/apps/portrait_width | FLOAT | 840 | 628 | 800 | 716 |
| window/apps/radius_bottom_left | FLOAT | 40 | 35 | 33 | 42 |
| window/apps/radius_bottom_right | FLOAT | 40 | 35 | 33 | 42 |
| window/apps/radius_top_left | FLOAT | 40 | 35 | 33 | 42 |
| window/apps/radius_top_right | FLOAT | 40 | 35 | 33 | 42 |
| window/container_extended_height | FLOAT | 0 | 0 | 1440 | 0 |
| window/container_height | FLOAT | 1440 | 1080 | 850 | 850 |
| window/container_width | FLOAT | 2560 | 1920 | 3840 | 3840 |
| window/overlay_guide/app_radius_bottom_left | FLOAT | 28 | 25 | 23 | 29 |
| window/overlay_guide/app_radius_bottom_right | FLOAT | 28 | 25 | 23 | 29 |
| window/overlay_guide/app_radius_top_left | FLOAT | 28 | 25 | 23 | 29 |
| window/overlay_guide/app_radius_top_right | FLOAT | 28 | 25 | 23 | 29 |
| window/overlay_guide/app_square | FLOAT | 100 | 88 | 83 | 106 |
| window/overlay_guide/gap_vertical | FLOAT | 32 | 28 | 28 | 36 |
| window/overlay_guide/padding_horizontal | FLOAT | 60 | 53 | 50 | 64 |
| window/overlay_guide/padding_vertical | FLOAT | 60 | 53 | 50 | 64 |

### form_factor_common ⟨f1e326⟩ — 72개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| font/line_height_sp/level1 | FLOAT | 24 | 21 | 20 | 26 |
| font/line_height_sp/level10 | FLOAT | 44 | 39 | 37 | 47 |
| font/line_height_sp/level11 | FLOAT | 48 | 42 | 40 | 51 |
| font/line_height_sp/level14 | FLOAT | 60 | 46 | 50 | 64 |
| font/line_height_sp/level19 | FLOAT | 80 | 65 | 66 | 84 |
| font/line_height_sp/level20 | FLOAT | 134 | 118 | 120 | 142 |
| font/line_height_sp/level3 | FLOAT | 28 | 25 | 23 | 29 |
| font/line_height_sp/level6 | FLOAT | 34 | 28 | 28 | 36 |
| font/line_height_sp/level7 | FLOAT | 36 | 32 | 30 | 38 |
| font/line_height_sp/level8 | FLOAT | 38 | 33 | 32 | 41 |
| font/line_height_sp/level9 | FLOAT | 40 | 35 | 33 | 42 |
| font/size_sp/level11 | FLOAT | 36 | 32 | 30 | 38 |
| font/size_sp/level13 | FLOAT | 40 | 35 | 33 | 42 |
| font/size_sp/level14 | FLOAT | 44 | 39 | 37 | 47 |
| font/size_sp/level15 | FLOAT | 48 | 42 | 40 | 51 |
| font/size_sp/level16 | FLOAT | 52 | 46 | 43 | 55 |
| font/size_sp/level18 | FLOAT | 60 | 53 | 50 | 64 |
| font/size_sp/level19 | FLOAT | 64 | 56 | 53 | 68 |
| font/size_sp/level2 | FLOAT | 18 | 16 | 15 | 19 |
| font/size_sp/level20 | FLOAT | 112 | 99 | 93 | 119 |
| font/size_sp/level5 | FLOAT | 24 | 21 | 20 | 26 |
| font/size_sp/level6 | FLOAT | 26 | 23 | 22 | 28 |
| font/size_sp/level7 | FLOAT | 28 | 25 | 23 | 29 |
| font/size_sp/level8 | FLOAT | 30 | 26 | 25 | 34 |
| font/size_sp/level9 | FLOAT | 32 | 28 | 27 | 35 |
| line/level1 | FLOAT | 1 | 1 | 1 | 1 |
| line/level2 | FLOAT | 2 | 2 | 2 | 3 |
| line/level3 | FLOAT | 3 | 3 | 3 | 3 |
| line/level4 | FLOAT | 4 | 4 | 3 | 4 |
| line/level5 | FLOAT | 6 | 5 | 5 | 5 |
| radius/bottom_left/level2 | FLOAT | 4 | 4 | 4 | 4 |
| radius/bottom_left/level3 | FLOAT | 8 | 7 | 7 | 9 |
| radius/bottom_left/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/bottom_left/level6 | FLOAT | 20 | 17 | 17 | 22 |
| radius/bottom_left/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/bottom_left/level9 | FLOAT | 32 | 28 | 27 | 35 |
| radius/bottom_left/max | FLOAT | 999 | 999 | 999 | 999 |
| radius/bottom_right/level2 | FLOAT | 4 | 4 | 4 | 4 |
| radius/bottom_right/level3 | FLOAT | 8 | 7 | 7 | 9 |
| radius/bottom_right/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/bottom_right/level6 | FLOAT | 20 | 17 | 17 | 22 |
| radius/bottom_right/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/bottom_right/level9 | FLOAT | 32 | 28 | 27 | 35 |
| radius/bottom_right/max | FLOAT | 999 | 999 | 999 | 999 |
| radius/top_left/level2 | FLOAT | 4 | 4 | 4 | 4 |
| radius/top_left/level3 | FLOAT | 8 | 7 | 7 | 9 |
| radius/top_left/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/top_left/level6 | FLOAT | 20 | 18 | 17 | 22 |
| radius/top_left/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/top_left/level9 | FLOAT | 32 | 28 | 27 | 35 |
| radius/top_left/max | FLOAT | 999 | 999 | 999 | 999 |
| radius/top_right/level2 | FLOAT | 4 | 4 | 4 | 4 |
| radius/top_right/level3 | FLOAT | 8 | 7 | 7 | 9 |
| radius/top_right/level5 | FLOAT | 16 | 14 | 13 | 17 |
| radius/top_right/level6 | FLOAT | 20 | 17 | 17 | 22 |
| radius/top_right/level7 | FLOAT | 24 | 21 | 20 | 26 |
| radius/top_right/level9 | FLOAT | 32 | 28 | 27 | 35 |
| radius/top_right/max | FLOAT | 999 | 999 | 999 | 999 |
| square/level11 | FLOAT | 52 | 46 | 43 | 55 |
| square/level12 | FLOAT | 56 | 49 | 46 | 59 |
| square/level13 | FLOAT | 60 | 53 | 50 | 64 |
| square/level16 | FLOAT | 72 | 63 | 60 | 77 |
| square/level18 | FLOAT | 80 | 70 | 66 | 84 |
| square/level22 | FLOAT | 100 | 88 | 83 | 106 |
| square/level6 | FLOAT | 32 | 28 | 27 | 35 |
| square/level7 | FLOAT | 36 | 28 | 30 | 41 |
| square/level8 | FLOAT | 40 | 35 | 33 | 42 |
| square/level9 | FLOAT | 44 | 39 | 37 | 49 |
| system/line_height/level10 | FLOAT | 68 | 68 | 68 | 68 |
| system/size/level13 | FLOAT | 48 | 48 | 48 | 48 |

### form_factor ⟨89cb27⟩ — 276개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| _driving_view/3dview/autoparking_trajectory_line | FLOAT | 40 | 35 | 40 | 0 |
| _driving_view/3dview/mask_bottom_gradient_height | FLOAT | 100 | 88 | 83 | 0 |
| _driving_view/3dview/mask_bottom_solid_height | FLOAT | 40 | 35 | 53 | 0 |
| _driving_view/3dview/mask_left_gradient_width | FLOAT | 100 | 88 | 83 | 0 |
| _driving_view/3dview/mask_right_gradient_width | FLOAT | 100 | 88 | 83 | 0 |
| _driving_view/3dview/mask_right_solid_width | FLOAT | 0 | 0 | 100 | 0 |
| _driving_view/3dview/mask_top_gradient_height | FLOAT | 200 | 176 | 166 | 0 |
| _driving_view/3dview/mask_top_solid_dynamic_height | FLOAT | 66 | 58 | 55 | 0 |
| _driving_view/3dview/mask_top_solid_height | FLOAT | 78 | 137 | 79 | 0 |
| _driving_view/3dview/parked_ring_line | FLOAT | 6 | 5 | 8 | 6 ← line/level5 |
| _driving_view/3dview/parking_icon_square | FLOAT | 80 ← square/level18 | 70 ← square/level18 | 66 ← square/level18 | 80 ← square/level18 |
| _driving_view/3dview/turning_point_line | FLOAT | 6 ← line/level5 | 5 ← line/level5 | 5 ← line/level5 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_charging_port_padding_left | FLOAT | 384 | 286 | 500 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_charging_port_padding_top | FLOAT | 160 | 116 | 220 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_frunk_padding_left | FLOAT | 180 | 132 | 224 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_frunk_padding_top | FLOAT | 264 | 196 | 352 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_sidemirror_padding_left | FLOAT | 420 | 314 | 530 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_sidemirror_padding_top | FLOAT | 456 | 330 | 540 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_trunk_padding_left | FLOAT | 648 | 480 | 796 | 0 |
| _driving_view/3dview/vehicle_control_frontquater_trunk_padding_top | FLOAT | 224 | 164 | 296 | 0 |
| _driving_view/3dview/vehicle_control_gap_horizontal | FLOAT | 16 | 14 | 13 | 0 |
| _driving_view/3dview/vehicle_control_height | FLOAT | 600 | 449 | 743 | 0 |
| _driving_view/3dview/vehicle_control_icon_square | FLOAT | 36 ← square/level7 | 28 ← square/level7 | 30 ← square/level7 | 36 ← square/level7 |
| _driving_view/3dview/vehicle_control_padding_vertical_top | FLOAT | 434 | 352 | 100 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_charging_port_padding_left | FLOAT | 342 | 254 | 436 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_charging_port_padding_top | FLOAT | 456 | 342 | 572 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_frunk_padding_left | FLOAT | 648 | 480 | 796 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_frunk_padding_top | FLOAT | 240 | 180 | 314 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_sidemirror_padding_top | FLOAT | 160 | 116 | 220 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_trunk_padding_left | FLOAT | 180 | 132 | 224 | 0 |
| _driving_view/3dview/vehicle_control_rearquater_trunk_padding_top | FLOAT | 264 | 196 | 352 | 0 |
| _driving_view/3dview/vehicle_control_ring_height | FLOAT | 204 | 152 | 252 | 0 |
| _driving_view/3dview/vehicle_control_ring_padding_top | FLOAT | 296 | 222 | 368 | 0 |
| _driving_view/3dview/vehicle_control_ring_width | FLOAT | 640 | 478 | 792 | 0 |
| _driving_view/3dview/vehicle_control_width | FLOAT | 840 | 628 | 1040 | 0 |
| _driving_view/3dview/vehicle_controlr_rearquater_sidemirror_padding_left | FLOAT | 420 | 314 | 530 | 0 |
| _driving_view/bottom/driving_mode_padding_horizontal | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/bottom/driving_mode_radius_bottom_left | FLOAT | 8 ← radius/bottom_left/level3 | 7 | 7 ← radius/bottom_left/level3 | 8 ← radius/bottom_left/level3 |
| _driving_view/bottom/driving_mode_radius_bottom_right | FLOAT | 8 ← radius/bottom_right/level3 | 7 | 8 ← radius/bottom_right/level3 | 8 ← radius/bottom_right/level3 |
| _driving_view/bottom/driving_mode_radius_top_left | FLOAT | 8 ← radius/top_left/level3 | 7 | 7 ← radius/top_left/level3 | 8 ← radius/top_left/level3 |
| _driving_view/bottom/driving_mode_radius_top_right | FLOAT | 8 ← radius/top_right/level3 | 7 | 8 ← radius/top_right/level3 | 8 ← radius/top_right/level3 |
| _driving_view/bottom/driving_mode_text_max_width | FLOAT | 220 | 194 | 183 | 0 |
| _driving_view/bottom/ipedal_padding_horizontal | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/bottom/ipedal_radius_bottom_left | FLOAT | 8 ← radius/bottom_left/level3 | 7 ← radius/bottom_left/level3 | 7 ← radius/bottom_left/level3 | 8 ← radius/bottom_left/level3 |
| _driving_view/bottom/ipedal_radius_top_left | FLOAT | 8 ← radius/top_left/level3 | 7 ← radius/top_left/level3 | 7 ← radius/top_left/level3 | 8 ← radius/top_left/level3 |
| _driving_view/bottom/ipedal_radius_top_right | FLOAT | 8 ← radius/top_right/level3 | 7 ← radius/top_right/level3 | 8 ← radius/top_right/level3 | 8 ← radius/top_right/level3 |
| _driving_view/bottom/left_gap_vertical | FLOAT | 12 | 11 | 10 | 0 |
| _driving_view/bottom/padding_horizontal | FLOAT | 20 | 18 | 20 | 0 |
| _driving_view/bottom/padding_vertical_bottom | FLOAT | 0 | 0 | 20 | 0 |
| _driving_view/bottom/regen_braking_height | FLOAT | 44 | 39 | 37 | 0 |
| _driving_view/bottom/regen_braking_width | FLOAT | 92 | 81 | 76 | 0 |
| _driving_view/left/group1/gap_vertical | FLOAT | -4 | -8 | -8 | 0 |
| _driving_view/left/group1/mph_gap_horizontal | FLOAT | 4 | 4 | 3 | 0 |
| _driving_view/left/group1/mph_padding_vertical_top | FLOAT | 22 | 20 | 18 | 0 |
| _driving_view/left/group1/padding_horizontal_left | FLOAT | 20 | 18 | 20 | 0 |
| _driving_view/left/group1/padding_vertical_top | FLOAT | 4 | 4 | 3 | 0 |
| _driving_view/left/group1/padding_vertical_top_systemcheck | FLOAT | 8 | 8 | 12 | 0 |
| _driving_view/left/group1/rpm_padding_vertical_top | FLOAT | 22 | 19 | 25 | 0 |
| _driving_view/left/group1/speedgroup_gap_vertical | FLOAT | -14 | -12 | -12 | 0 |
| _driving_view/left/group1/speedometer_gap_vertical | FLOAT | -16 | -14 | -13 | 0 |
| _driving_view/left/group1/speedometer_number_padding_left | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/left/group1/speedometer_number_width | FLOAT | 200 | 177 | 166 | 0 |
| _driving_view/left/group2/ad_indicator_auto_height | FLOAT | 24 | 21 | 20 | 0 |
| _driving_view/left/group2/ad_indicator_auto_radius_bottom_left | FLOAT | 4 ← radius/bottom_left/level2 | 4 ← radius/bottom_left/level2 | 4 ← radius/bottom_left/level2 | 4 ← radius/bottom_left/level2 |
| _driving_view/left/group2/ad_indicator_auto_radius_bottom_right | FLOAT | 4 ← radius/bottom_right/level2 | 4 ← radius/bottom_right/level2 | 4 ← radius/bottom_right/level2 | 4 ← radius/bottom_right/level2 |
| _driving_view/left/group2/ad_indicator_auto_radius_top_left | FLOAT | 4 ← radius/top_left/level2 | 4 ← radius/top_left/level2 | 4 ← radius/top_left/level2 | 4 ← radius/top_left/level2 |
| _driving_view/left/group2/ad_indicator_auto_radius_top_right | FLOAT | 4 ← radius/top_right/level2 | 4 ← radius/top_right/level2 | 4 ← radius/top_right/level2 | 4 ← radius/top_right/level2 |
| _driving_view/left/group2/ad_indicator_auto_square | FLOAT | 24 | 21 | 20 | 0 |
| _driving_view/left/group2/ad_indicator_autopark_p_padding_horizontal_left | FLOAT | 28 | 25 | 23 | 0 |
| _driving_view/left/group2/ad_indicator_autopark_p_padding_vertical_top | FLOAT | 23 | 20 | 19 | 0 |
| _driving_view/left/group2/ad_indicator_autopark_p_square | FLOAT | 32 ← square/level6 | 28 ← square/level6 | 27 ← square/level6 | 0 |
| _driving_view/left/group2/ad_indicator_gap_horizontal | FLOAT | 4 | 3 | 3 | 0 |
| _driving_view/left/group2/ad_indicator_gap_vertical | FLOAT | -10 | -9 | -8 | 0 |
| _driving_view/left/group2/ad_indicator_icon_square | FLOAT | 60 ← square/level13 | 53 ← square/level13 | 50 ← square/level13 | 60 ← square/level13 |
| _driving_view/left/group2/ad_indicator_ready_height | FLOAT | 60 | 46 | 50 | 0 |
| _driving_view/left/group2/ad_indicator_ready_width | FLOAT | 46 | 40 | 38 | 0 |
| _driving_view/left/group2/ad_indicator_square | FLOAT | 100 ← square/level22 | 88 ← square/level22 | 83 ← square/level22 | 100 ← square/level22 |
| _driving_view/left/group2/doorlock_switch_container_height | FLOAT | 80 | 70 | 66 | 0 |
| _driving_view/left/group2/doorlock_switch_height | FLOAT | 64 | 56 | 53 | 0 |
| _driving_view/left/group2/doorlock_switch_icon_square | FLOAT | 32 ← square/level6 | 28 ← square/level6 | 27 ← square/level6 | 32 ← square/level6 |
| _driving_view/left/group2/doorlock_switch_knob_height | FLOAT | 48 | 42 | 40 | 0 |
| _driving_view/left/group2/doorlock_switch_knob_radius_bottom_left | FLOAT | 999 ← radius/bottom_right/max | 999 ← radius/bottom_right/max | 999 ← radius/bottom_right/max | 999 ← radius/bottom_right/max |
| _driving_view/left/group2/doorlock_switch_knob_radius_bottom_right | FLOAT | 999 ← radius/bottom_left/max | 999 ← radius/bottom_left/max | 999 ← radius/bottom_left/max | 999 ← radius/bottom_left/max |
| _driving_view/left/group2/doorlock_switch_knob_radius_top_left | FLOAT | 999 ← radius/top_left/max | 999 ← radius/top_left/max | 999 ← radius/top_left/max | 999 ← radius/top_left/max |
| _driving_view/left/group2/doorlock_switch_knob_radius_top_right | FLOAT | 999 ← radius/top_right/max | 999 ← radius/top_right/max | 999 ← radius/top_right/max | 999 ← radius/top_right/max |
| _driving_view/left/group2/doorlock_switch_knob_width | FLOAT | 56 | 49 | 46 | 0 |
| _driving_view/left/group2/doorlock_switch_padding_horizontal | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/left/group2/doorlock_switch_padding_vertical | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/left/group2/doorlock_switch_radius_bottom_left | FLOAT | 999 ← radius/bottom_left/max | 999 ← radius/bottom_left/max | 999 ← radius/bottom_left/max | 999 ← radius/bottom_left/max |
| _driving_view/left/group2/doorlock_switch_radius_top_left | FLOAT | 999 ← radius/top_left/max | 999 ← radius/top_left/max | 999 ← radius/top_left/max | 999 ← radius/top_left/max |
| _driving_view/left/group2/doorlock_switch_radius_top_right | FLOAT | 999 ← radius/top_right/max | 999 ← radius/top_right/max | 999 ← radius/top_right/max | 999 ← radius/top_right/max |
| _driving_view/left/group2/doorlock_switch_width | FLOAT | 112 | 99 | 93 | 0 |
| _driving_view/left/group2/indicator_group_gap_vertical | FLOAT | 16 | 14 | 13 | 0 |
| _driving_view/left/group2/padding_horizontal_left | FLOAT | 16 | 14 | 16 | 0 |
| _driving_view/left/group2/padding_vertical_top | FLOAT | 24 | 21 | 12 | 0 |
| _driving_view/left/group2/sign_current_camera_height | FLOAT | 24 | 21 | 20 | 0 |
| _driving_view/left/group2/sign_current_camera_width | FLOAT | 48 | 42 | 40 | 0 |
| _driving_view/left/group2/sign_current_distance_average_speed_padding_horizontal | FLOAT | 4 | 3 | 3 | 0 |
| _driving_view/left/group2/sign_current_distance_average_speed_padding_vertical | FLOAT | 12 | 11 | 10 | 0 |
| _driving_view/left/group2/sign_current_distance_height | FLOAT | 52 | 46 | 43 | 0 |
| _driving_view/left/group2/sign_current_distance_padding_vertical | FLOAT | 7 | 6 | 5 | 0 |
| _driving_view/left/group2/sign_current_distance_radius_bottom_left | FLOAT | 8 ← radius/bottom_left/level3 | 7 ← radius/bottom_left/level3 | 8 ← radius/bottom_right/level3 | 0 |
| _driving_view/left/group2/sign_current_distance_radius_bottom_right | FLOAT | 8 ← radius/bottom_right/level3 | 7 ← radius/bottom_right/level3 | 8 ← radius/bottom_right/level3 | 0 |
| _driving_view/left/group2/sign_current_distance_radius_top_left | FLOAT | 8 ← radius/top_left/level3 | 7 ← radius/top_left/level3 | 7 ← radius/top_left/level3 | 0 |
| _driving_view/left/group2/sign_current_distance_radius_top_right | FLOAT | 8 ← radius/top_right/level3 | 7 ← radius/top_right/level3 | 8 ← radius/top_right/level3 | 0 |
| _driving_view/left/group2/sign_current_distance_width | FLOAT | 108 | 95 | 90 | 0 |
| _driving_view/left/group2/sign_current_gap_vertical | FLOAT | -10 | -9 | -8 | 0 |
| _driving_view/left/group2/sign_current_number_only_padding_top | FLOAT | 26 | 23 | 22 | 0 |
| _driving_view/left/group2/sign_current_number_with_sub_padding_top | FLOAT | 33 | 29 | 26 | 0 |
| _driving_view/left/group2/sign_current_square | FLOAT | 100 | 88 | 83 | 0 |
| _driving_view/left/group2/sign_next_square | FLOAT | 72 ← square/level16 | 63 ← square/level16 | 60 ← square/level16 | 72 ← square/level16 |
| _driving_view/left/group2/sign1_group_vertical_gap | FLOAT | -24 | -21 | -20 | 0 |
| _driving_view/left/group2/sign1_mask_height | FLOAT | 40 | 35 | 33 | 0 |
| _driving_view/top/group1/gap_horizontal | FLOAT | 8 | 0 | 8 | 0 |
| _driving_view/top/group1/gap_vertical | FLOAT | 10 | 9 | 8 | 0 |
| _driving_view/top/group1/guage_height | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/top/group1/padding_horizontal | FLOAT | 20 | 18 | 28 | 0 |
| _driving_view/top/group1/power_guage_ev_gap_horizontal | FLOAT | 2 | 2 | 2 | 0 |
| _driving_view/top/group1/power_guage_height | FLOAT | 36 | 32 | 36 | 0 |
| _driving_view/top/group1/power_guage_radius_bottom_left | FLOAT | 8 ← radius/bottom_left/level3 | 7 ← radius/bottom_left/level3 | 7 ← radius/bottom_left/level3 | 0 |
| _driving_view/top/group1/power_guage_radius_bottom_right | FLOAT | 8 ← radius/bottom_right/level3 | 7 ← radius/bottom_right/level3 | 8 ← radius/bottom_right/level3 | 0 |
| _driving_view/top/group1/power_guage_radius_top_left | FLOAT | 8 ← radius/top_left/level3 | 7 ← radius/top_left/level3 | 7 ← radius/top_left/level3 | 0 |
| _driving_view/top/group1/power_guage_radius_top_right | FLOAT | 8 ← radius/top_right/level3 | 7 ← radius/top_right/level3 | 8 ← radius/top_right/level3 | 0 |
| _driving_view/top/group1/power_guage_state_icon_square | FLOAT | 32 | 28 | 32 | 0 |
| _driving_view/top/group1/power_guage_width | FLOAT | 68 | 60 | 68 | 0 |
| _driving_view/top/group1/powertrain_height | FLOAT | 60 | 53 | 64 | 0 |
| _driving_view/top/group1/powertrain_icon_square | FLOAT | 48 | 42 | 48 | 0 |
| _driving_view/top/group1/prnd_gap_horizontal | FLOAT | 4 | 3 | 4 | 0 |
| _driving_view/top/group1/prnd_unit_selected_square | FLOAT | 48 | 42 | 48 | 0 |
| _driving_view/top/group1/prnd_unit_selected_square_radius_bottom_left | FLOAT | 8 | 7 | 8 | 0 |
| _driving_view/top/group1/prnd_unit_selected_square_radius_bottom_right | FLOAT | 8 | 7 | 8 | 0 |
| _driving_view/top/group1/prnd_unit_selected_square_radius_top_left | FLOAT | 8 | 7 | 8 | 0 |
| _driving_view/top/group1/prnd_unit_selected_square_radius_top_right | FLOAT | 8 | 7 | 8 | 0 |
| _driving_view/top/group1/prnd_unit_width | FLOAT | 30 | 26 | 30 | 0 |
| _driving_view/top/group2/gap_horizontal | FLOAT | 6 | 5 | 4 | 0 |
| _driving_view/top/group2/gap_vertical | FLOAT | 6 | 5 | 4 | 0 |
| _driving_view/top/group2/max_height | FLOAT | 258 | 227 | 105 | 0 |
| _driving_view/top/group2/min_height | FLOAT | 0 | 0 | 50 | 0 |
| _driving_view/top/group2/padding_horizontal_left | FLOAT | 232 | 151 | 193 | 0 |
| _driving_view/top/group2/padding_horizontal_right | FLOAT | 20 | 18 | 28 | 0 |
| _driving_view/top/group2/padding_vertical_top | FLOAT | 18 | 15 | 5 | 8 |
| _driving_view/top/group2/telltale_safety_height | FLOAT | 222 | 253 | 134 | 0 |
| _driving_view/top/group2/telltale_square | FLOAT | 60 | 53 | 50 | 0 |
| _driving_view/top/group3/gap_vertical | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/top/group3/minitbt_gap_horizontal | FLOAT | 16 | 14 | 13 | 0 |
| _driving_view/top/group3/minitbt_icon_square | FLOAT | 80 | 70 | 66 | 0 |
| _driving_view/top/group3/minitbt_padding_horizontal_left | FLOAT | 8 | 7 | 7 | 0 |
| _driving_view/top/group3/minitbt_padding_horizontal_right | FLOAT | 20 | 18 | 17 | 0 |
| _driving_view/top/group3/minitbt_padding_vertical | FLOAT | 12 | 11 | 10 | 0 |
| _driving_view/top/group3/minitbt_radius_bottom_left | FLOAT | 24 ← radius/bottom_left/level7 | 21 ← radius/bottom_left/level7 | 20 ← radius/bottom_left/level7 | 24 ← radius/bottom_left/level7 |
| _driving_view/top/group3/minitbt_radius_bottom_right | FLOAT | 24 ← radius/bottom_right/level7 | 21 ← radius/bottom_right/level7 | 20 ← radius/bottom_right/level7 | 24 ← radius/bottom_right/level7 |
| _driving_view/top/group3/minitbt_radius_top_left | FLOAT | 24 ← radius/top_left/level7 | 21 ← radius/top_left/level7 | 20 ← radius/top_left/level7 | 24 ← radius/top_left/level7 |
| _driving_view/top/group3/minitbt_radius_top_right | FLOAT | 24 ← radius/top_right/level7 | 21 ← radius/top_right/level7 | 20 ← radius/top_right/level7 | 24 ← radius/top_right/level7 |
| _driving_view/top/group3/minitbt_text_gap_vertical | FLOAT | -4 | -3 | -3 | 0 |
| _driving_view/top/group3/minitbt_text_max_width | FLOAT | 416 | 366 | 345 | 0 |
| _driving_view/top/group3/minitbt_text_min_width | FLOAT | 200 | 176 | 166 | 0 |
| _driving_view/top/group3/padding_horizontal_left | FLOAT | 20 | 135 | 17 | 0 |
| _driving_view/top/group3/padding_horizontal_right | FLOAT | 20 | 18 | 17 | 0 |
| _driving_view/top/group3/padding_vertical_top | FLOAT | 24 | 21 | 20 | 0 |
| _driving_view/top/mask_gradient_height | FLOAT | 80 | 70 | 66 | 0 |
| buttons/basic/disabled_border | FLOAT | 2 ← line/level2 | 2 ← line/level2 | 2 ← line/level2 | 2 ← line/level2 |
| buttons/basic/large/gap_horizontal | FLOAT | 16 | 14 | 14 | 17 |
| buttons/basic/large/height | FLOAT | 100 | 88 | 84 | 106 |
| buttons/basic/large/icon_sqaure | FLOAT | 52 ← square/level11 | 46 ← square/level11 | 43 ← square/level11 | 52 ← square/level11 |
| buttons/basic/large/min_width | FLOAT | 140 | 123 | 116 | 148 |
| buttons/basic/large/padding_horizontal | FLOAT | 40 | 35 | 34 | 42 |
| buttons/basic/large/padding_vertical | FLOAT | 16 | 14 | 14 | 17 |
| buttons/basic/large/radius_top_left | FLOAT | 32 ← radius/top_left/level9 | 28 ← radius/top_left/level9 | 27 ← radius/top_left/level9 | 32 ← radius/top_left/level9 |
| buttons/basic/medium/gap_horizontal | FLOAT | 14 | 12 | 12 | 15 |
| buttons/basic/medium/height | FLOAT | 80 | 70 | 66 | 84 |
| buttons/basic/medium/icon_sqaure | FLOAT | 40 ← square/level8 | 35 ← square/level8 | 33 ← square/level8 | 40 ← square/level8 |
| buttons/basic/medium/min_width | FLOAT | 120 | 106 | 100 | 128 |
| buttons/basic/medium/padding_horizontal | FLOAT | 32 | 28 | 27 | 35 |
| buttons/basic/medium/padding_vertical | FLOAT | 16 | 14 | 14 | 17 |
| buttons/basic/medium/radius_top_left_medium | FLOAT | 24 ← radius/top_left/level7 | 21 ← radius/top_left/level7 | 20 ← radius/top_left/level7 | 24 ← radius/top_left/level7 |
| buttons/basic/small/gap_horizontal | FLOAT | 12 | 11 | 10 | 13 |
| buttons/basic/small/height | FLOAT | 64 | 56 | 54 | 68 |
| buttons/basic/small/icon_sqaure | FLOAT | 32 ← square/level6 | 28 ← square/level6 | 27 ← square/level6 | 32 ← square/level6 |
| buttons/basic/small/min_width | FLOAT | 100 | 88 | 84 | 106 |
| buttons/basic/small/padding_horizontal | FLOAT | 24 | 21 | 20 | 26 |
| buttons/basic/small/padding_vertical | FLOAT | 8 | 7 | 8 | 10 |
| buttons/basic/small/radius_bottom_right | FLOAT | 16 ← radius/bottom_right/level5 | 14 ← radius/bottom_right/level5 | 13 ← radius/bottom_right/level5 | 16 ← radius/bottom_right/level5 |
| buttons/circle/container_normal | FLOAT | 80 | 80 | 80 | 80 |
| buttons/circle/container_overlap | FLOAT | 50 | 50 | 50 | 50 |
| buttons/circle/icon_normal | FLOAT | 44 | 44 | 44 | 44 |
| buttons/icon/circle_radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/circle_radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/circle_radius_top_left | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/circle_radius_top_right | FLOAT | 999 | 999 | 999 | 999 |
| buttons/icon/medium/content | FLOAT | 56 ← square/level12 | 49 ← square/level12 | 46 ← square/level12 | 56 ← square/level12 |
| buttons/icon/medium/rounded_radius_bottom_left | FLOAT | 20 ← radius/bottom_left/level6 | 17 ← radius/bottom_left/level6 | 17 ← radius/bottom_left/level6 | 20 ← radius/bottom_left/level6 |
| buttons/icon/medium/rounded_radius_bottom_right | FLOAT | 20 ← radius/bottom_right/level6 | 17 ← radius/bottom_right/level6 | 17 ← radius/bottom_right/level6 | 20 ← radius/bottom_right/level6 |
| buttons/icon/medium/rounded_radius_top_left | FLOAT | 20 ← radius/top_left/level6 | 18 ← radius/top_left/level6 | 17 ← radius/top_left/level6 | 20 ← radius/top_left/level6 |
| buttons/icon/medium/rounded_radius_top_right | FLOAT | 20 ← radius/top_right/level6 | 17 ← radius/top_right/level6 | 17 ← radius/top_right/level6 | 20 ← radius/top_right/level6 |
| buttons/icon/medium/width | FLOAT | 80 | 70 | 66 | 84 |
| buttons/icon/small/content | FLOAT | 44 ← square/level9 | 39 ← square/level9 | 37 ← square/level9 | 44 ← square/level9 |
| buttons/icon/small/rounded_radius_bottom_left | FLOAT | 16 ← radius/bottom_left/level5 | 14 ← radius/bottom_left/level5 | 13 ← radius/bottom_left/level5 | 16 ← radius/bottom_left/level5 |
| buttons/icon/small/rounded_radius_bottom_right | FLOAT | 16 ← radius/bottom_right/level5 | 14 ← radius/bottom_right/level5 | 13 ← radius/bottom_right/level5 | 16 ← radius/bottom_right/level5 |
| buttons/icon/small/rounded_radius_top_left | FLOAT | 16 ← radius/top_left/level5 | 14 ← radius/top_left/level5 | 13 ← radius/top_left/level5 | 16 ← radius/top_left/level5 |
| buttons/icon/small/rounded_radius_top_right | FLOAT | 16 ← radius/top_right/level5 | 14 ← radius/top_right/level5 | 13 ← radius/top_right/level5 | 16 ← radius/top_right/level5 |
| buttons/icon/small/width | FLOAT | 64 | 56 | 54 | 68 |
| imagery/cover/border | FLOAT | 2 ← line/level2 | 2 ← line/level2 | 2 ← line/level2 | 2 ← line/level2 |
| imagery/cover/radius_bottom_left | FLOAT | 20 ← radius/bottom_left/level6 | 17 ← radius/bottom_left/level6 | 17 ← radius/bottom_left/level6 | 20 ← radius/bottom_left/level6 |
| imagery/cover/radius_bottom_right | FLOAT | 20 ← radius/bottom_right/level6 | 17 ← radius/bottom_right/level6 | 17 ← radius/bottom_right/level6 | 20 ← radius/bottom_right/level6 |
| imagery/cover/radius_top_left | FLOAT | 20 ← radius/top_left/level6 | 18 ← radius/top_left/level6 | 17 ← radius/top_left/level6 | 20 ← radius/top_left/level6 |
| imagery/cover/radius_top_right | FLOAT | 20 ← radius/top_right/level6 | 17 ← radius/top_right/level6 | 17 ← radius/top_right/level6 | 20 ← radius/top_right/level6 |
| notification/border | FLOAT | 1 ← line/level1 | 1 ← line/level1 | 1 ← line/level1 | 1 ← line/level1 |
| notification/gap_medium | FLOAT | 16 | 14 | 14 | 17 |
| notification/padding_medium | FLOAT | 24 | 21 | 20 | 26 |
| notification/padding_small | FLOAT | 16 | 14 | 14 | 16 |
| notification/vehicle/gap_horizontal | FLOAT | 16 | 14 | 14 | 18 |
| notification/vehicle/image_side_padding_vertical | FLOAT | 16 | 14 | 14 | 18 |
| notification/vehicle/images/large_width | FLOAT | 160 | 141 | 134 | 172 |
| notification/vehicle/images/medium_square | FLOAT | 128 | 113 | 106 | 136 |
| notification/vehicle/images/small_square | FLOAT | 64 | 56 | 54 | 69 |
| notification/vehicle/max_height | FLOAT | 176 | 155 | 146 | 187 |
| notification/vehicle/max_width | FLOAT | 540 | 475 | 448 | 573 |
| notification/vehicle/padding_vertical | FLOAT | 16 | 14 | 14 | 18 |
| notification/vehicle/radius_bottom_left | FLOAT | 24 ← radius/bottom_left/level7 | 21 ← radius/bottom_left/level7 | 20 ← radius/bottom_left/level7 | 24 ← radius/bottom_left/level7 |
| notification/vehicle/radius_bottom_right | FLOAT | 24 ← radius/bottom_right/level7 | 21 ← radius/bottom_right/level7 | 20 ← radius/bottom_right/level7 | 24 ← radius/bottom_right/level7 |
| notification/vehicle/radius_top_left | FLOAT | 24 ← radius/top_left/level7 | 21 ← radius/top_left/level7 | 20 ← radius/top_left/level7 | 24 ← radius/top_left/level7 |
| notification/vehicle/radius_top_right | FLOAT | 24 ← radius/top_right/level7 | 21 ← radius/top_right/level7 | 20 ← radius/top_right/level7 | 24 ← radius/top_right/level7 |
| notification/vehicle/text_max_height | FLOAT | 144 | 127 | 120 | 154 |
| notification/vehicle/text_min_width | FLOAT | 200 | 176 | 166 | 212 |
| notification/vehicle/text_only_max_width | FLOAT | 500 | 440 | 412 | 532 |
| notification/vehicle/text_side_padding_vertical | FLOAT | 20 | 18 | 18 | 23 |
| notification/vehicle/text_with_icon_image_max_width | FLOAT | 284 | 250 | 232 | 302 |
| notification/vehicle/text_with_icon_max_width | FLOAT | 428 | 377 | 348 | 454 |
| statusbar/gap_small | FLOAT | 8 | 8 | 8 | 8 |
| statusbar/height_medium | FLOAT | 80 | 80 | 80 | 80 |
| utility/border | FLOAT | 1 ← line/level1 | 1 ← line/level1 | 1 ← line/level1 | 1 ← line/level1 |
| utility/button_min_width | FLOAT | 160 | 141 | 133 | 0 |
| utility/handle/arrow_height | FLOAT | 16 | 14 | 13 | 0 |
| utility/handle/arrow_line | FLOAT | 6 ← line/level5 | 5 ← line/level5 | 5 ← line/level5 | 6 ← line/level5 |
| utility/handle/arrow_width | FLOAT | 56 | 49 | 46 | 0 |
| utility/handle/height | FLOAT | 40 | 35 | 33 | 0 |
| utility/handle/line_width | FLOAT | 80 | 70 | 66 | 0 |
| utility/handle/padding_vertical_top | FLOAT | 12 | 11 | 10 | 0 |
| utility/icon_square | FLOAT | 64 | 56 | 53 | 0 |
| utility/item_gap_large | FLOAT | 24 | 21 | 20 | 0 |
| utility/item_gap_medium | FLOAT | 16 | 14 | 13 | 0 |
| utility/item_height | FLOAT | 112 | 100 | 94 | 0 |
| utility/padding_bottom | FLOAT | 24 | 21 | 20 | 0 |
| utility/padding_horizontal | FLOAT | 24 | 21 | 20 | 0 |
| utility/page_indicator_dot_square | FLOAT | 8 | 7 | 7 | 0 |
| utility/page_indicator_gap_horizontal | FLOAT | 10 | 9 | 8 | 0 |
| utility/radius_bottom_left | FLOAT | 32 ← radius/bottom_left/level9 | 28 ← radius/bottom_left/level9 | 27 ← radius/bottom_left/level9 | 32 ← radius/bottom_left/level9 |
| utility/radius_bottom_right | FLOAT | 32 ← radius/bottom_right/level9 | 28 ← radius/bottom_right/level9 | 27 ← radius/bottom_right/level9 | 32 ← radius/bottom_right/level9 |
| utility/radius_top_left | FLOAT | 32 ← radius/top_left/level9 | 28 ← radius/top_left/level9 | 27 ← radius/top_left/level9 | 32 ← radius/top_left/level9 |
| utility/radius_top_right | FLOAT | 32 ← radius/top_right/level9 | 28 ← radius/top_right/level9 | 27 ← radius/top_right/level9 | 32 ← radius/top_right/level9 |
| utility/title_padding_horizontal | FLOAT | 16 | 14 | 13 | 0 |
| utility/title_width | FLOAT | 224 | 197 | 186 | 0 |
| utility/tpms/detail_error_container_radius_bottom_left | FLOAT | 16 ← radius/bottom_left/level5 | 14 ← radius/bottom_left/level5 | 13 ← radius/bottom_left/level5 | 0 |
| utility/tpms/detail_error_container_radius_bottom_right | FLOAT | 16 ← radius/bottom_right/level5 | 14 ← radius/bottom_right/level5 | 13 ← radius/bottom_right/level5 | 0 |
| utility/tpms/detail_error_container_radius_top_left | FLOAT | 16 ← radius/top_left/level5 | 14 ← radius/top_left/level5 | 13 ← radius/top_left/level5 | 0 |
| utility/tpms/detail_error_container_radius_top_right | FLOAT | 16 ← radius/top_right/level5 | 14 ← radius/top_right/level5 | 13 ← radius/top_right/level5 | 0 |
| utility/tpms/detail_error_gap_horizontal | FLOAT | 8 | 7 | 7 | 0 |
| utility/tpms/detail_error_gap_vertical | FLOAT | 8 | 7 | 7 | 0 |
| utility/tpms/detail_error_icon_square | FLOAT | 28 | 25 | 24 | 0 |
| utility/tpms/detail_padding_horizontal | FLOAT | 16 | 14 | 13 | 0 |
| utility/tpms/detail_psi_gap_vertical | FLOAT | 4 | 3 | 3 | 0 |
| utility/tpms/detail_psi_padding_vertical_bottom | FLOAT | 4 | 3 | 3 | 0 |
| utility/tpms/detail_psi_padding_vertical_top | FLOAT | 4 | 3 | 3 | 0 |
| utility/tpms/detail_vehicle_image_piece_square | FLOAT | 56 | 50 | 47 | 0 |
| utility/width | FLOAT | 800 | 592 | 660 | 0 |
| window/apps/driving_view_height | FLOAT | 1280 | 939 | 850 | 850 |
| window/apps/driving_view_width | FLOAT | 840 | 628 | 1040 | 1280 |
| window/apps/portrait_width | FLOAT | 840 | 628 | 800 | 716 |

### language_units ⟨c47d17⟩ — 2개, modes: Unit 1 / Unit 2

| name | type | Unit 1 | Unit 2 |
|---|---|---|---|
| KM/Speed 2 | STRING | "MPH" | "KM/H" |

### Number ⟨60241c⟩ — 5개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| Common/8 | FLOAT | 8 |
| Radius/l | FLOAT | 24 |
| Radius/m | FLOAT | 16 |
| Radius/xs | FLOAT | 0 |
| Spacing/0 | FLOAT | 0 |

### gleo.sys.color ⟨faa653⟩ — 1개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| gleo_blue | COLOR | #596BFF |

### Color0 ⟨49c4e4⟩ — 1개, modes: Light0 / Dark0

| name | type | Light0 | Dark0 |
|---|---|---|---|
| Icon/gray-tertiary | COLOR | #131417 α64% | #131417 α64% |

### typo ⟨48b778⟩ — 4개, modes: connect-s / connect-l / connect-w / connect-waterfall

| name | type | connect-s | connect-l | connect-w | connect-waterfall |
|---|---|---|---|---|---|
| line_height_sp/level17 | FLOAT | 72 | 65 | 60 | 77 |
| line_height_sp/level20 | FLOAT | 134 | 118 | 156 | 142 |
| size_sp/level17 | FLOAT | 56 | 49 | 46 | 59 |
| size_sp/level20 | FLOAT | 112 | 99 | 130 | 119 |

### Color ⟨b11af6⟩ — 6개, modes: Light / Dark

| name | type | Light | Dark |
|---|---|---|---|
| static/dark/100 | COLOR | #131417 | #131417 |
| static/dark/400 | COLOR | #131417 α32% | #131417 α32% |
| static/light/100 | COLOR | #FFFFFF | #FFFFFF |
| static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% |
| text/dark/primary | COLOR | #131417 ← static/dark/100 | #FFFFFF ← static/light/100 |
| text/dark/quaternary | COLOR | #131417 α32% ← static/dark/400 | #FFFFFF α32% ← static/light/400 |

### brand ⟨ece444⟩ — 1개, modes: hyundai / kia / genesis

| name | type | hyundai | kia | genesis |
|---|---|---|---|---|
| ivi_font/text | STRING | "HyundaiSansUI_JP_KR_Latin" | "KiaSignatureUI_JP_KR_Latin" | "GenesisSansUI_JP_KR_Latin" |

### theme ⟨6d660e⟩ — 50개, modes: Hyundai-Light / Hyundai-Dark / Kia-Light / Kia-Dark / Genesis-Light / Genesis-Dark

| name | type | Hyundai-Light | Hyundai-Dark | Kia-Light | Kia-Dark | Genesis-Light | Genesis-Dark |
|---|---|---|---|---|---|---|---|
| 100 | COLOR | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF |
| color_hmc/container_positive/pressed | COLOR | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 |
| color_hmc/shadow/alpha_1 | COLOR | #000000 α8% | #000000 α8% | #000000 α8% | #000000 α8% | #000000 α8% | #000000 α8% |
| color_hmc/shadow/alpha_2 | COLOR | #000000 α16% | #000000 α16% | #000000 α16% | #000000 α16% | #000000 α16% | #000000 α16% |
| color_hmc/stroke/focused | COLOR | #6ABFFB | #6ABFFB | #6ABFFB | #6ABFFB | #6ABFFB | #6ABFFB |
| color_hmc/stroke/focused_pressed | COLOR | #0794F8 | #0794F8 | #0794F8 | #0794F8 | #0794F8 | #0794F8 |
| color_hmc/theme | STRING | "Hyundai" | "Hyundai" | "Kia" | "Kia" | "Genesis" | "Genesis" |
| color_hmc/various/blue_1 | COLOR | #0064FF | #0064FF | #0064FF | #0064FF | #0064FF | #0064FF |
| color_hmc/various/blue_2 | COLOR | #297FFF | #297FFF | #297FFF | #297FFF | #297FFF | #297FFF |
| color_hmc/various/green_3 | COLOR | #00BA13 | #00BA13 | #00BA13 | #00BA13 | #00BA13 | #00BA13 |
| color_hmc/various/green_5 | COLOR | #0EE87E | #0EE87E | #0EE87E | #0EE87E | #0EE87E | #0EE87E |
| color_hmc/various/orange_3 | COLOR | #FFA202 | #FFA202 | #FFA202 | #FFA202 | #FFA202 | #FFA202 |
| color_hmc/various/red_1 | COLOR | #FE3D16 | #FE3D16 | #FE3D16 | #FE3D16 | #FE3D16 | #FE3D16 |
| color_hmc/various/red_5 | COLOR | #F24239 | #F24239 | #F24239 | #F24239 | #F24239 | #F24239 |
| color/alpha/dark/100 | COLOR | #131417 α10% | #FFFFFF α10% | #131417 α10% | #FFFFFF α10% | #131417 α10% | #FFFFFF α10% |
| color/alpha/dark/200 | COLOR | #131417 α20% | #FFFFFF α20% | #131417 α20% | #FFFFFF α20% | #131417 α20% | #FFFFFF α20% |
| color/alpha/dark/300 | COLOR | #131417 α30% | #FFFFFF α30% | #131417 α30% | #FFFFFF α30% | #131417 α30% | #FFFFFF α30% |
| color/alpha/dark/400 | COLOR | #131417 α40% | #FFFFFF α40% | #131417 α40% | #FFFFFF α40% | #131417 α40% | #FFFFFF α40% |
| color/alpha/dark/500 | COLOR | #131417 α50% | #FFFFFF α50% | #131417 α50% | #FFFFFF α50% | #131417 α50% | #FFFFFF α50% |
| color/background/primary | COLOR | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 |
| color/background/wide_s2 | COLOR | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 | #EDEDF1 | #151515 |
| color/basic/00 | COLOR | #FFFFFF | #131417 | #FFFFFF | #131417 | #FFFFFF | #131417 |
| color/basic/100 | COLOR | #EDEEF2 | #313236 | #EDEEF2 | #313236 | #EDEEF2 | #313236 |
| color/basic/300 | COLOR | #C7C9CE | #686A72 | #C7C9CE | #686A72 | #C7C9CE | #686A72 |
| color/basic/50 | COLOR | #F7F8FA | #1D1E21 | #F7F8FA | #1D1E21 | #F7F8FA | #1D1E21 |
| color/basic/500 | COLOR | #686A72 | #C7C9CE | #686A72 | #C7C9CE | #686A72 | #C7C9CE |
| color/basic/700 | COLOR | #313236 | #EDEEF2 | #313236 | #EDEEF2 | #313236 | #EDEEF2 |
| color/button/basic/pressed | COLOR | #131417 α20% ← color_hmc/container_positive/pressed | #FFFFFF α20% ← color_hmc/container_positive/pressed | #131417 α20% ← color_hmc/container_positive/pressed | #FFFFFF α20% ← color_hmc/container_positive/pressed | #131417 α20% ← color_hmc/container_positive/pressed | #FFFFFF α20% ← color_hmc/container_positive/pressed |
| color/button/filled/pressed | COLOR | #313236 ← color/basic/700 | #C7C9CE ← color/basic/500 | #313236 ← color/basic/700 | #C7C9CE ← color/basic/500 | #313236 ← color/basic/700 | #C7C9CE ← color/basic/500 |
| color/divider/secondary | COLOR | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 | #131417 α20% ← color/alpha/dark/200 | #FFFFFF α20% ← color/alpha/dark/200 |
| color/divider/tertiary | COLOR | #131417 α30% ← color/alpha/dark/300 | #FFFFFF α30% ← color/alpha/dark/300 | #131417 α30% ← color/alpha/dark/300 | #FFFFFF α30% ← color/alpha/dark/300 | #131417 α30% ← color/alpha/dark/300 | #FFFFFF α30% ← color/alpha/dark/300 |
| color/gnb/pressed | COLOR | #131417 α10% ← color/alpha/dark/100 | #FFFFFF α10% ← color/alpha/dark/100 | #131417 α10% ← color/alpha/dark/100 | #FFFFFF α10% ← color/alpha/dark/100 | #131417 α10% ← color/alpha/dark/100 | #FFFFFF α10% ← color/alpha/dark/100 |
| color/icon/dark/primary | COLOR | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 |
| color/icon/dark/tertiary | COLOR | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 | #131417 α64% ← color/static/dark/300 | #FFFFFF α64% ← color/static/light/300 |
| color/informative/active | COLOR | #00BA13 ← color_hmc/various/green_3 | #0EE87E ← color_hmc/various/green_5 | #00BA13 ← color_hmc/various/green_3 | #0EE87E ← color_hmc/various/green_5 | #00BA13 ← color_hmc/various/green_3 | #0EE87E ← color_hmc/various/green_5 |
| color/informative/error | COLOR | #FFA202 ← color_hmc/various/orange_3 | #FFA202 ← color_hmc/various/orange_3 | #FFA202 ← color_hmc/various/orange_3 | #FFA202 ← color_hmc/various/orange_3 | #FFA202 ← color_hmc/various/orange_3 | #FFA202 ← color_hmc/various/orange_3 |
| color/informative/negative | COLOR | #FE3D16 ← color_hmc/various/red_1 | #F24239 ← color_hmc/various/red_5 | #FE3D16 ← color_hmc/various/red_1 | #F24239 ← color_hmc/various/red_5 | #FE3D16 ← color_hmc/various/red_1 | #F24239 ← color_hmc/various/red_5 |
| color/informative/positive | COLOR | #0064FF ← color_hmc/various/blue_1 | #297FFF ← color_hmc/various/blue_2 | #0064FF ← color_hmc/various/blue_1 | #297FFF ← color_hmc/various/blue_2 | #0064FF ← color_hmc/various/blue_1 | #297FFF ← color_hmc/various/blue_2 |
| color/regulation/bluetooth | COLOR | #297FFF ← color_hmc/various/blue_2 | #297FFF ← color_hmc/various/blue_2 | #297FFF ← color_hmc/various/blue_2 | #297FFF ← color_hmc/various/blue_2 | #297FFF ← color_hmc/various/blue_2 | #297FFF ← color_hmc/various/blue_2 |
| color/regulation/green | COLOR | #00BA13 ← color_hmc/various/green_3 | #00BA13 ← color_hmc/various/green_3 | #00BA13 ← color_hmc/various/green_3 | #00BA13 ← color_hmc/various/green_3 | #00BA13 ← color_hmc/various/green_3 | #00BA13 ← color_hmc/various/green_3 |
| color/static/dark/100 | COLOR | #131417 | #131417 | #131417 | #131417 | #131417 | #131417 |
| color/static/dark/300 | COLOR | #131417 α64% | #131417 α64% | #131417 α64% | #131417 α64% | #131417 α64% | #131417 α64% |
| color/static/dark/400 | COLOR | #131417 α32% | #131417 α32% | #131417 α32% | #131417 α32% | #131417 α32% | #131417 α32% |
| color/static/light/100 | COLOR | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF |
| color/static/light/200 | COLOR | #FFFFFF α84% | #FFFFFF α84% | #FFFFFF α84% | #FFFFFF α84% | #FFFFFF α84% | #FFFFFF α84% |
| color/static/light/300 | COLOR | #FFFFFF α64% | #FFFFFF α64% | #FFFFFF α64% | #FFFFFF α64% | #FFFFFF α64% | #FFFFFF α64% |
| color/static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% |
| color/text/dark/primary | COLOR | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 |
| color/text/dark/quaternary | COLOR | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 |
| driving_view/cid/background_wide | COLOR | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 | #F7F8FA ← color/basic/50 | #1D1E21 ← color/basic/50 | #F6F8FA | #000000 |

### form_factor ⟨a35806⟩ — 63개, modes: connect-s / connect-l / connect-w / connect-waterfall / connect-s-14

| name | type | connect-s | connect-l | connect-w | connect-waterfall | connect-s-14 |
|---|---|---|---|---|---|---|
| app_market/app_item/app_icon_border | FLOAT | 1 | 1 | 1 | 1 | 1 |
| bars/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 | 999 |
| bars/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 | 999 |
| bars/radius_top_left | FLOAT | 999 | 999 | 999 | 999 | 999 |
| bars/radius_top_right | FLOAT | 999 | 999 | 999 | 999 | 999 |
| bars/window_control/container_height | FLOAT | 200 | 176 | 240 | 192 | 200 |
| bars/window_control/container_width | FLOAT | 20 | 18 | 30 | 24 | 16 |
| bars/window_control/handle_height | FLOAT | 160 | 141 | 132 | 128 | 160 |
| bars/window_control/handle_height_pressed | FLOAT | 80 | 70 | 66 | 66 | 80 |
| bars/window_control/handle_width | FLOAT | 4 | 4 | 8 | 6 | 4 |
| bars/window_control/padding_horizontal | FLOAT | 8 | 7 | 16 | 8 | 8 |
| gnb/application/app_activate_height | FLOAT | 8 | 7 | 8 | 8 | 8 |
| gnb/application/app_activate_width | FLOAT | 8 | 7 | 8 | 8 | 8 |
| gnb/application/app_square | FLOAT | 84 | 74 | 70 | 70 | 74 |
| gnb/controller/arrow_height | FLOAT | 0 | 0 | 100 | 80 | 0 |
| gnb/controller/arrow_icon_sqaure | FLOAT | 64 | 56 | 53 | 53 | 52 |
| gnb/controller/arrow_width | FLOAT | 80 | 70 | 108 | 108 | 64 |
| gnb/controller/pressed_square | FLOAT | 100 | 88 | 96 | 96 | 88 |
| gnb/gap_small | FLOAT | 4 | 4 | 4 | 4 | 4 |
| gnb/menu/container_square | FLOAT | 124 | 109 | 108 | 94 | 96 |
| gnb/menu/HMG_app_icon_radius | FLOAT | 24 | 21 | 20 | 18 | 20 |
| gnb/menu/radius_bottom_left | FLOAT | 30 | 25 | 30 | 24 | 30 |
| gnb/menu/radius_bottom_right | FLOAT | 30 | 25 | 30 | 24 | 30 |
| gnb/menu/radius_top_left | FLOAT | 30 | 25 | 30 | 24 | 30 |
| gnb/menu/radius_top_right | FLOAT | 30 | 25 | 30 | 24 | 30 |
| gnb/remove_button/background_square | FLOAT | 40 | 35 | 36 | 36 | 40 |
| gnb/remove_button/container_square | FLOAT | 44 | 39 | 40 | 40 | 44 |
| gnb/remove_button/icon_height | FLOAT | 3 | 3 | 4 | 4 | 3 |
| gnb/remove_button/icon_width | FLOAT | 16 | 14 | 16 | 16 | 16 |
| gnb/temperature/container_width | FLOAT | 164 | 116 | 108 | 108 | 132 |
| gnb/temperature/decimal_places_padding_top | FLOAT | 12 | 11 | 14 | 0 | 6 |
| gnb/temperature/decimal_places_witdh | FLOAT | 38 | 33 | 32 | 32 | 36 |
| gnb/temperature/gap_horizontal | FLOAT | -2 | -2 | -4 | -6 | -2 |
| gnb/temperature/number_width | FLOAT | 75 | 69 | 58 | 58 | 60 |
| gnb/temperature/wind_icon_square | FLOAT | 48 | 40 | 27 | 27 | 40 |
| gnb/width | FLOAT | 0 | 0 | 140 | 112 | 0 |
| HMG_expansion/focus_border | FLOAT | 6 | 6 | 6 | 6 | 6 |
| imagery/cover/border | FLOAT | 2 | 2 | 2 | 2 | 2 |
| imagery/profile/radius_bottom_left | FLOAT | 999 | 999 | 999 | 999 | 999 |
| imagery/profile/radius_bottom_right | FLOAT | 999 | 999 | 999 | 999 | 999 |
| imagery/profile/radius_top_left | FLOAT | 999 | 999 | 999 | 999 | 999 |
| imagery/profile/radius_top_right | FLOAT | 999 | 999 | 999 | 999 | 999 |
| statusbar/height | FLOAT | 96 | 84 | 850 | 680 | 76 |
| statusbar/HMG_extended_height_2 | FLOAT | 0 | 0 | 1440 | 0 | 0 |
| statusbar/HMG_width_2 | FLOAT | 0 | 0 | 100 | 80 | 0 |
| statusbar/menus/container_radius_bottom_left | FLOAT | 20 | 18 | 17 | 17 | 20 |
| statusbar/menus/container_radius_bottom_right | FLOAT | 20 | 18 | 17 | 17 | 20 |
| statusbar/menus/container_radius_top_left | FLOAT | 20 | 18 | 17 | 17 | 20 |
| statusbar/menus/container_radius_top_right | FLOAT | 20 | 18 | 17 | 17 | 20 |
| statusbar/menus/container_square | FLOAT | 80 | 68 | 80 | 80 | 64 |
| statusbar/menus/HMG_icon_call | FLOAT | 180 | 152 | 80 | 72 | 144 |
| statusbar/menus/HMG_icon_voice | FLOAT | 100 | 86 | 80 | 72 | 80 |
| statusbar/menus/icon_square | FLOAT | 64 | 54 | 48 | 48 | 52 |
| statusbar/menus/privacy/dot | FLOAT | 16 | 12 | 14 | 12 | 12 |
| statusbar/menus/privacy/padding | FLOAT | 8 | 6 | 10 | 8 | 8 |
| statusbar/menus/profile_square | FLOAT | 80 | 60 | 70 | 70 | 56 |
| window/apps/application_height | FLOAT | 1184 | 855 | 810 | 664 | 948 |
| window/apps/HMG_radius_app | FLOAT | 40 | 35 | 33 | 33 | 32 |
| window/apps/landscape_width | FLOAT | 1700 | 1274 | 1640 | 1256 | 1360 |
| window/apps/portrait_width | FLOAT | 840 | 628 | 800 | 648 | 672 |
| window/container_extended_height | FLOAT | 0 | 0 | 1440 | 0 | 0 |
| window/container_height | FLOAT | 1440 | 1080 | 850 | 680 | 1152 |
| window/container_width | FLOAT | 2560 | 1920 | 3840 | 3072 | 2048 |

### theme ⟨652601⟩ — 6개, modes: Hyundai-Light / Hyundai-Dark / Kia-Light / Kia-Dark / Genesis-Light / Genesis-Dark

| name | type | Hyundai-Light | Hyundai-Dark | Kia-Light | Kia-Dark | Genesis-Light | Genesis-Dark |
|---|---|---|---|---|---|---|---|
| color/icon/dark/primary | COLOR | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 | #131417 ← color/static/dark/100 | #FFFFFF ← color/static/light/100 |
| color/icon/dark/quaternary | COLOR | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 | #131417 α32% ← color/static/dark/400 | #FFFFFF α32% ← color/static/light/400 |
| color/static/dark/100 | COLOR | #131417 | #131417 | #131417 | #131417 | #131417 | #131417 |
| color/static/dark/400 | COLOR | #131417 α32% | #131417 α32% | #131417 α32% | #131417 α32% | #131417 α32% | #131417 α32% |
| color/static/light/100 | COLOR | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF | #FFFFFF |
| color/static/light/400 | COLOR | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% | #FFFFFF α32% |

### brand ⟨d88cde⟩ — 1개, modes: hyundai / kia / genesis

| name | type | hyundai | kia | genesis |
|---|---|---|---|---|
| ivi_font/text | STRING | "HyundaiSansUI_JP_KR_Latin" | "KiaSignatureUI_JP_KR_Latin" | "GenesisSansUI_JP_KR_Latin" |

### form_factor_common ⟨c36b9d⟩ — 7개, modes: connect-s / connect-l / connect-w / connect-waterfall / connect-s-14

| name | type | connect-s | connect-l | connect-w | connect-waterfall | connect-s-14 |
|---|---|---|---|---|---|---|
| font/line_height_dp/level11 | FLOAT | 48 | 42 | 42 | 42 | 42 |
| font/line_height_dp/level14 | FLOAT | 60 | 46 | 50 | 50 | 46 |
| font/line_height_dp/level5 | FLOAT | 32 | 28 | 30 | 30 | 28 |
| font/size_dp/level12 | FLOAT | 38 | 33 | 34 | 32 | 33 |
| font/size_dp/level15 | FLOAT | 46 | 42 | 40 | 40 | 42 |
| font/size_dp/level5 | FLOAT | 24 | 21 | 23 | 23 | 21 |
| font/size_sp/level12 | FLOAT | 38 | 33 | 34 | 34 | 33 |

### Number ⟨7cee4f⟩ — 1개, modes: Mode 1

| name | type | Mode 1 |
|---|---|---|
| Radius/xs | FLOAT | 0 |

### form_factor_common ⟨3b1ce0⟩ — 2개, modes: connect-s / connect-l / connect-w / connect-waterfall / connect-s-14

| name | type | connect-s | connect-l | connect-w | connect-waterfall | connect-s-14 |
|---|---|---|---|---|---|---|
| font/line_height_dp/level14 | FLOAT | 60 | 46 | 50 | 50 | 46 |
| font/size_dp/level15 | FLOAT | 46 | 42 | 40 | 40 | 42 |

## Text styles

| name | family | style | size | lineHeight | letterSpacing |
|---|---|---|---|---|---|
| Body/B3 | 12/Regular400 | 42dot Sans OTF | Regular | 12 | 1.5 RAW | 0 PERCENT |
| Body/Large/Normal | 42dot Sans OTF | Regular | 30 | 38 PIXELS | 0 PERCENT |
| Body/Large/Strong | 42dot Sans OTF | Bold | 30 | 38 PIXELS | 0 PERCENT |
| Body/Medium/Normal | 42dot Sans OTF | Regular | 28 | 36 PIXELS | 0 PERCENT |
| Body/Medium/Strong | 42dot Sans OTF | Bold | 28 | 36 PIXELS | 0 PERCENT |
| Body/Small/dp_Strong | HyundaiSansUI_JP_KR_Latin | SemiBold | 24 | 32 PIXELS | 0 PERCENT |
| Body/Small/Normal | 42dot Sans OTF | Regular | 26 | 34 PIXELS | 0 PERCENT |
| Body/Small/Strong | 42dot Sans OTF | Bold | 26 | 34 PIXELS | 0 PERCENT |
| Driving/AD_speed | 42dot Sans OTF | ExtraBold | 48 | 60 PIXELS | -3 PERCENT |
| Driving/Speed_kmh | Asta Sans | Bold | 112 | 134 PIXELS | -3 PERCENT |
| Driving/Speed_MPH | 42dot Sans OTF | Bold | 36 | 44 PIXELS | -3 PERCENT |
| Driving/Traffic_Sign | 42dot Sans OTF | ExtraBold | 40 | 48 PIXELS | -3 PERCENT |
| Driving/Traffic_Sign_Sub | 42dot Sans OTF | Bold | 18 | 24 PIXELS | -4 PERCENT |
| Headline/Large/Extra_Strong | 42dot Sans OTF | ExtraBold | 60 | 80 PIXELS | 0 PERCENT |
| Headline/Medium/Extra_Strong | Asta Sans | ExtraBold | 56 | 72 PIXELS | 0 PERCENT |
| Headline/Medium/Strong | Asta Sans | Bold | 56 | 72 PIXELS | 0 PERCENT |
| Headline/Small/dp_Extra_Strong | HyundaiSansUI_JP_KR_Latin | Bold | 46 | 60 PIXELS | 0 PERCENT |
| Headline/Small/dp_Normal (HMG) | HyundaiSansUI_JP_KR_Latin | Regular | 46 | 60 PIXELS | 0 PERCENT |
| Headline/Small/dp_Strong | HyundaiSansUI_JP_KR_Latin | SemiBold | 46 | 60 PIXELS | 0 PERCENT |
| Headline/Small/Extra_Strong | Asta Sans | ExtraBold | 48 | 64 PIXELS | 0 PERCENT |
| Headline/Small/Strong | Asta Sans | Bold | 48 | 64 PIXELS | 0 PERCENT |
| Label/Medium | 42dot Sans OTF | Regular | 24 | 28 PIXELS | 0 PERCENT |
| Title/Large/dp_Extra_Strong | HyundaiSansUI_JP_KR_Latin | Bold | 38 | 48 PIXELS | 0 PERCENT |
| Title/Large/dp_Normal | HyundaiSansUI_JP_KR_Latin | Regular | 38 | 48 PIXELS | 0 PERCENT |
| Title/Large/dp_Strong | HyundaiSansUI_JP_KR_Latin | SemiBold | 38 | 48 PIXELS | 0 PERCENT |
| Title/Large/Strong | 42dot Sans OTF | Bold | 40 | 52 PIXELS | 0 PERCENT |
| Title/Medium/Extra_Strong | 42dot Sans OTF | ExtraBold | 36 | 44 PIXELS | 0 PERCENT |
| Title/Medium/Strong | Asta Sans | Bold | 36 | 44 PIXELS | 0 PERCENT |
| Title/Small/Normal | 42dot Sans OTF | Regular | 32 | 40 PIXELS | 0 PERCENT |
| Title/Small/Strong | 42dot Sans OTF | Bold | 32 | 40 PIXELS | 0 PERCENT |

## Effect styles

- **Shadow/Light/Level1**: DROP_SHADOW x0 y6 blur24 spread0 #000000 α8%
- **Shadow/Strong**: DROP_SHADOW x0 y12 blur48 spread0 #000000 α20%
- **Shadow/Light/Level2**: DROP_SHADOW x0 y8 blur32 spread0 #000000 α16%; DROP_SHADOW x0 y2 blur2 spread0 #000000 α8%
- **Shadow/Light/Level2**: DROP_SHADOW x0 y8 blur32 spread0 #000000 α16%; DROP_SHADOW x0 y2 blur2 spread0 #000000 α8%
- **App icon Lighting_Dark**: INNER_SHADOW x0 y-1 blur1 spread0 #FFFFFF α10%; INNER_SHADOW x0 y1 blur1 spread0 #FFFFFF α25%
- **Shadow/Light/Level1**: DROP_SHADOW x0 y6 blur24 spread0 #000000 α8%
- **Shadow/Light/Level2**: DROP_SHADOW x0 y8 blur32 spread0 #000000 α16%; DROP_SHADOW x0 y2 blur2 spread0 #000000 α8%
- **App icon Lighting_Dark**: INNER_SHADOW x0 y-1 blur1 spread0 #FFFFFF α10%; INNER_SHADOW x0 y1 blur1 spread0 #FFFFFF α25%

## Fill styles

- **Core/Desaturated/Neutral950**: #252525
- **Core/Desaturated/Neutral950**: #252525
- **Secondary/Basic/Positive**: #00BA13
- **Secondary/Basic/Nagative**: #FF2E00
- **Core/White**: #FFFFFF
