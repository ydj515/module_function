# SimpleCaptcha 음성 기능

- 기존 문자만 나오던 captcha에 음성 기능 추가
- 알파벳 음성 추가

## perBrowserBranch.js
- 브라우저 별 음성기능 분기 처리 script

### 사용법
1. lib 폴더에 있는 simplecaptchar-dj.jar를 복사
2. pom.xml에 아래 추가
```xml
<dependency>
    <groupId>simplecaptcha-dj</groupId>
    <artifactId>simplecaptcha-dj</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${basedir}/src/main/webapp/WEB-INF/lib/simplecaptcha-dj.jar</systemPath>
</dependency>
```
