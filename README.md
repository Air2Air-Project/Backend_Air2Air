Air2Air Backend
=============
### 경남 지역 대기 오염 물질 예측 시스템
> 경남지역의 대기 오염 물질 농도를 실시간으로 모니터링하고, 1,2,3시간 후의 대기 오염 물질의 
농도를 예측합니다. 1시간 후의 예측 결과를 기반으로 대기 오염 물질 농도가 건강에 유해한 수준인지 수치값으로 나타내어 사용자에게 제공해 주는 프로젝트입니다.

## 개발환경
### 개발기간
2024.07.01 ~ 2024.07.28

### 프로젝트 팀 구성 및 역할
|백엔드|프런트엔드|프런트엔드|데이터분석|
|:---:|:---:|:---:|:---:|
|이영인|김우정|배지현|이세련|
|<a href="https://github.com/Air2Air-Project/Backend_Air2Air" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|<a href="https://github.com/Air2Air-Project/FrontEnd_Air2Air" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|<a href="https://github.com/Air2Air-Project/FrontEnd_Air2Air" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|<a href="https://github.com/Air2Air-Project/DA_Air2Air" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|

### 프레임워크
<img width="1280" alt="Screenshot_1" src="https://github.com/user-attachments/assets/7e69cef0-dc14-4a71-89da-8454e23437d8">

### 시스템 구조
<img width="1279" alt="Screenshot_3" src="https://github.com/user-attachments/assets/717b5b08-cab6-4b17-9bb1-6d332f60391c">

## 주요기능
1. JWT 토큰 방식을 사용한 사용자 회원가입, 로그인
2. 기상청 API를 통한 경남 지역의 6가지 대기 공해 정보(미세먼지, 초미세먼지, 오존, 이산화황, 일산화탄소, 이산화질소 농도), 바람 정보  실시간 제공
3. 1, 2, 3시간 후의 6가지 대기 공해 정보 예측 및 결과 시각화
4. 1시간 후의 6가지 대기 공해 예측 결과를 기반으로 도시공해지수, 대기환경지수, 야외활동지수 제공
5. 문의 게시판
    - 사용자가 게시물 작성, 수정, 삭제 가능
    - 관리자가 댓글 작성, 수정, 삭제 가능

## 구현 결과
#### [ 클릭하여 재생해주세요 ]
[![Video Label](https://github.com/user-attachments/assets/dc6e6dc4-b295-4614-a1da-d6223187fae4)](https://www.youtube.com/watch?v=zqDrgOylsWw)

## EER-Diagram
<img width="1280" alt="Screenshot_2" src="https://github.com/user-attachments/assets/289ebc92-ee59-4223-8a60-410951dae514">

## REST API
