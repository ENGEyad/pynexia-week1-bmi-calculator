# BMI Calculator

A native Android calculator that validates height and weight, computes BMI, and displays the corresponding adult category.

Pynexia Android training / Week 1. Java 17 + XML.

[Hosted showcase](https://engeyad.github.io/pynexia-week1-bmi-calculator/) · [Report PDF](https://engeyad.github.io/pynexia-week1-bmi-calculator/downloads/BMI-Calculator-Report.pdf) · [APK](https://engeyad.github.io/pynexia-week1-bmi-calculator/downloads/BMI-Calculator.apk) · [Submission ZIP](https://engeyad.github.io/pynexia-week1-bmi-calculator/downloads/BMI-Calculator.zip)

## Run

Open this repository root in Android Studio. Install SDK 35 / Build Tools 35.0.0; use JDK 17 or 21. Sync Gradle and run `bmi` on Android 8.0 / API 26 or later. Initial sync needs internet. AGP 8.9.1; Gradle Wrapper 8.11.1.

```powershell
.\gradlew.bat :bmi:assembleDebug :bmi:testDebugUnitTest :bmi:lintDebug
.\gradlew.bat :bmi:connectedDebugAndroidTest
```

The second command needs an emulator/device. On macOS/Linux, use `chmod +x gradlew` then `./gradlew`. APK output: `bmi/build/outputs/apk/debug/bmi-debug.apk`.

## Implementation

### Inputs and validation

Height: 50-300 cm. Weight: 10-700 kg. Empty, zero, negative, malformed and nonfinite inputs are rejected. Arabic digits and decimal commas are supported.

### Calculation and category

BMI = weight in kg / height in metres squared. Categories use the unrounded value: below 18.5, below 25, below 30, or 30 and above.

### Result handling

The result uses two decimal places. Input edits clear stale results; Reset clears the form. The calculated result survives Activity recreation.

## Review walkthrough

1. Enter height 175 cm and weight 70 kg, then tap Calculate BMI.
2. Confirm the result is 22.86 with the Normal category.
3. Change an input: the old result disappears until recalculation.
4. Try empty fields, zero or malformed input, then Reset. Rotate after a valid calculation to check restoration.

## Verification

4 JVM tests and 2 device tests passed, with zero failures, errors or skips. Lint: 0 errors and 6 warnings, without suppression. Four JVM tests cover calculation, category boundaries, localized parsing and invalid values. Two device tests cover calculation, clearing/resetting and Activity recreation.

Tested 25 October 2026 on a Pixel 8 emulator, Android 17 / API 37 (37.1 image), x86_64. No physical-device or API 26 run. See `verification/` for executed reports; local filesystem prefixes are redacted. Debug APK signature verified.

## Scope

Categories apply to adults aged 20 and over. This is an educational screening calculator, not a diagnosis. Input ranges are guardrails rather than clinical cutoffs.

APKs are debug-signed builds for assessment. The hosted page is a project showcase with actual screenshots and downloads, not an in-browser Android runtime. Publication does not submit the assignment to Pynexia.

## شرح عربي

يحسب التمرين مؤشر كتلة الجسم من الطول بالسنتيمتر والوزن بالكيلوغرام، ثم يعرض النتيجة وتصنيفها. يفحص المدخلات ويدعم الأرقام العربية والفاصلة العشرية. يعتمد التصنيف على القيمة الأصلية قبل التقريب، وتُمسح النتيجة السابقة عند تعديل المدخلات.

للتجربة: أدخل طولًا قدره ١٧٥ سم ووزنًا قدره ٧٠ كغم. النتيجة المتوقعة ٢٢٫٨٦ ضمن المجال الطبيعي. جرّب بعد ذلك قيمة صفرية أو حقلًا فارغًا للتحقق من رسائل الخطأ. التصنيفات للبالغين بعمر ٢٠ سنة فأكثر.

## Submission

Copy `SUBMISSION-NOTES.txt` into the notes field. Repository URL: https://github.com/ENGEyad/pynexia-week1-bmi-calculator

Hosted URL: https://engeyad.github.io/pynexia-week1-bmi-calculator/

Attach the ZIP or PDF. The APK is inside the ZIP, since the supplied form does not accept direct APK attachments.

Reference: https://www.cdc.gov/bmi/adult-calculator/bmi-categories.html
