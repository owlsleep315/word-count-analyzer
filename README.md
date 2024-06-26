## 단어 빈도 분석기

[word-count-analyzer](http://word-count-analyzer.ap-northeast-2.elasticbeanstalk.com/)

단어 빈도 분석기는 PDF 파일에서 단어 빈도를 분석하고 시각화하는 웹 애플리케이션입니다.

사용자는 PDF 파일을 업로드하면 상위 단어와 그 빈도를 확인할 수 있습니다.

또한 단어의 의미를 확인할 수 있는 기능을 제공합니다.

---

### 주요 기능
- PDF 파일 업로드 및 단어 빈도 분석
- 사용자가 지정한 개수만큼 상위 단어 출력
- 단어에 마우스를 올리면 해당 단어의 뜻을 툴팁으로 표시
- 분석 중 로딩 스피너를 통해 진행 상황 표시
- 반응형 디자인으로 모바일 환경에서도 최적화된 화면 제공

---

### 기술 스택
- 프론트엔드: HTML, CSS, JavaScript, Vue.js, Bootstrap
- 백엔드: Java, Spring Boot, Apache PDFBox, Komoran, Naver 검색 API
- 배포: AWS Elastic Beanstalk
