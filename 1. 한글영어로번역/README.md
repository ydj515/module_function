# 한글 영어로 번역
- 각 번역 api를 이용하여 한글 -> 영어로 번역하여 엑셀에 저장
- 공통 상수는 constant.py에 빼 놓음

## papago.py
- 네이버 파파고 번역 api를 이용해 xslx에 저장
- 네이버 개발자 센터에서 키 발급 받아서 사용
- https://developers.naver.com/apps/#/register
- 위의 링크에서 어플리케이션 등록하고 파파고 api 등록하고 사용

### 필요 lib
```bash
pip install urllib
pip install openpyxl
```

## google.py
- google 번역 api를 이용해 csv에 저장

### 필요 lib
```bash
pip install googletrans
```