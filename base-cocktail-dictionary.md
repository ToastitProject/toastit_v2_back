# 칵테일 관리 시스템 용어사전

| 한글명 | 영문명 | 설명 |
|-------|--------|------|
| 칵테일 | Cocktail | 여러 가지 재료를 혼합하여 만든 음료로, 시스템에서 관리하는 기본 단위 |
| 기본 칵테일 | Original Cocktail | 널리 알려진 기존의 칵테일, 시스템 관리자가 등록하며 표준화된 레시피를 가짐 |
| 커스텀 칵테일 | Custom Cocktail | 기존 레시피를 변형하거나 새롭게 만든 칵테일, 사용자가 직접 등록 |
| 칵테일 이름 | Cocktail Name | 칵테일을 구분하는 고유한 식별자(한글명, 영문명 포함) |
| 재료 | Ingredient | 칵테일을 만드는데 사용되는 구성 요소 |
| 대표 재료 | Main Ingredients | 칵테일을 대표하는 주요 재료(레시피 등록 시 첫 번째와 두 번째 재료) |
| 알코올 | Alcoholic Ingredient | 알코올을 함유한 재료로, 별도의 상세 정보 페이지를 제공 |
| 용량 | Amount | 재료의 정확한 사용량(숫자 + 단위) |
| 레시피 | Recipe | 칵테일 제조를 위한 재료와 방법을 설명하는 정보 |
| 제조 단계 | Production Step | 칵테일을 만드는 순서와 방법, 단계별로 번호가 매겨짐 |
| 알코올 도수 계산 | ABV Calculation | 칵테일에 포함된 알코올 재료의 용량과 도수를 바탕으로 전체 도수를 계산하는 방식 |
| 알코올 도수 | ABV | Alcohol By Volume의 약자, 음료에 포함된 알코올의 비율(%) |
| 알코올 유무 | Alcoholic Type | 알코올 함유 여부(알코올/무알코올) |
| 칵테일 맛 | Cocktail Taste | 칵테일의 주된 맛 특성(달콤함, 신맛, 쓴맛, 짠맛, 매운맛, 부드러운 맛 등) |
| 재료 맛 | Ingredient Taste | 재료(특히 알코올)의 맛 특성 |
| 칵테일 분류 | Cocktail Category | 칵테일의 종류를 구분하는 기준(예: 하이볼, 마티니 등) |
| 재료 분류 | Ingredient Category | 재료(특히 알코올)의 세부 분류(예: 테네시 위스키, 스카치 위스키 등) |
| 찜하기 | Like | 사용자가 특정 칵테일을 관심 목록에 추가하는 기능 |
| 찜한 목록 | Like List | 사용자가 관심있는 칵테일을 모아둔 목록 |
| 사용자 | User | 시스템을 사용하는 이용자 |
| 정렬 | Sorting | 칵테일 목록을 특정 기준(이름, 좋아요, 도수 등)에 따라 순서대로 배열 |
| 무한 스크롤 | Infinite Scroll | 페이지 하단에 도달했을 때 추가 콘텐츠를 자동으로 로드하는 방식 |
| 재료 유형 | Ingredient Type | 재료의 종류 구분(주류/기타) |
| 원산지 | Country of Origin | 주류의 생산 국가 |
| 숙성 기간 | Aging Period | 주류가 숙성된 기간 |
| 증류 방식 | Distillation Method | 주류의 제조 과정에서 사용된 증류 방식 |
| 원재료 | Raw Materials | 재료를 만드는 데 사용된 기본 성분 |
| 원재료 함량 | Raw Material Percentage | 주류 제조에 사용된 원재료의 비율(%) |
| 정형 데이터 | Structured Data | 구조화된 형태로 저장되는 데이터(MySQL) |
| 비정형 데이터 | Unstructured Data | 구조가 고정되지 않은 데이터(예: 제조 과정, 비표준 용량 단위)로 MongoDB에 저장 |
| 이미지 URL | Image URL | 이미지 파일의 웹 주소 |
| 술의 역사 | History of Alcohol | 알코올의 역사적 유래나 배경 정보 |
| 리뷰 | Review | 사용자가 칵테일에 대해 작성한 평가와 의견 (향후 구현 예정) |
| 기본 칵테일 표시 | Original Label | 칵테일 목록에서 기본 칵테일을 표시하는 "ORIGINAL" 라벨 |
| 커스텀 칵테일 표시 | Custom Label | 칵테일 목록에서 커스텀 칵테일을 표시하는 "CUSTOM" 라벨 |
| 탭 메뉴 | Tab Menu | 칵테일 목록을 필터링하는 상단 메뉴(ALL, ORIGINAL, CUSTOM 등) |
| 정렬 옵션 | Sorting Options | 칵테일 목록의 정렬 기준을 선택할 수 있는 옵션 |
| 알코올 상세 기본 정보 | Basic Alcohol Details | MySQL에 저장되는 알코올의 기본적인 정형 데이터(분류, 원산지, 알코올 도수 등) |
| 알코올 상세 추가 정보 | Additional Alcohol Details | MongoDB에 저장되는 알코올의 비정형 데이터(숙성 기간, 증류 방식, 원재료 함량, 술의 역사 등) |
| 맛 특성 | Taste Profile | 음료나 재료의 맛에 대한 특징적인 설명(예: 우수수, 호밀, 부드러움) |
| Elasticsearch | Elasticsearch | 검색 및 분석 엔진으로, 시스템의 칵테일 검색 기능 구현에 사용 |
| MongoDB | MongoDB | 비정형 데이터 저장에 사용되는 NoSQL 데이터베이스 |
| 검색량 | Search Volume | 특정 칵테일에 대한 검색 횟수 |
| 조회수 | View Count | 레시피 조회 횟수 |
| 좋아요 수 | Like Count | 회원 간 좋아요를 표시한 횟수 |
| 노출 순서 | Display Order | 항목이 나열되는 순서 |
| 사용자 오류 | User Error | 사용자 입력에 관련된 오류 |
| 서비스 오류 | Service Error | 시스템이나 서버와 관련된 오류 |
| 데이터베이스 오류 | Database Error | 데이터베이스와 관련된 오류 |
| 화면 처리 오류 | Display Error | 화면 출력과 관련된 오류 |
| 악의적인 행동 | Malicious Behavior | 시스템에 해를 끼치려는 사용자 행동 |
| 중복된 데이터 | Duplicate Data | 데이터베이스에 이미 존재하는 동일한 데이터 |
