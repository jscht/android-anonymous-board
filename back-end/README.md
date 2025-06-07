## 📘 프로젝트, Sequelize CLI 사용 예시 요약

이 프로젝트는 Sequelize ORM을 기반으로 데이터베이스 테이블 생성, 마이그레이션, 시딩 등을 CLI를 통해 관리합니다.

### ✅ 필수 사전 설치

```bash
npm install --save sequelize sequelize-cli
npm install --save mysql2     # 또는 사용 중인 DB에 맞는 드라이버 (pg, sqlite3 등)
```

<br>

## 🚀 명령어 요약

| 단계 | 설명                                      | 명령어                                         |
|------|-------------------------------------------|------------------------------------------------|
| 1    | Sequelize 프로젝트 구조 초기화            | `npm run db:init`                              |
| 2    | 모델 및 마이그레이션 생성 (초기 설정용)   | `npm run db:model`                             |
| 3    | 마이그레이션 파일 수정 (옵션 조정 등)     | 직접 수정                                      |
| 4    | 마이그레이션 실행 (테이블 생성)           | `npm run db:migrate`                           |
| 5    | 시더 파일 생성                            | `npm run db:seed:generate`                     |
| 6    | 시더 파일에 더미 데이터 작성              | 직접 수정                                      |
| 7    | 시더 실행 (더미 데이터 삽입)              | `npm run db:seed`                              |
| 8    | 서버 실행 (개발 모드)                     | `npm run dev`                                  |
| 9    | 서버 실행 (운영 모드)                     | `npm start`                                    |

<br>

## ⏪ 롤백 명령어 (선택)

| 설명                         | 명령어                                      |
|------------------------------|---------------------------------------------|
| 최근 마이그레이션 롤백       | `npm run db:migrate:undo`                  |
| 전체 시더 롤백               | `npm run db:seed:undo`                     |
