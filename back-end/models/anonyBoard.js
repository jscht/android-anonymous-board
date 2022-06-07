'use strict';
module.exports = (sequelize, DataTypes) => {
    
    const anonyBoards = sequelize.define("anonyBoards", {
        id: { // 게시글번호
            type: DataTypes.INTEGER,
            autoIncrement: true,
            primaryKey: true,
        },
        title: { // 제목
            type: DataTypes.STRING(50),
            allowNull: false,
        },
        subject: { // 본문
            type: DataTypes.STRING,
            allowNull: false,
        },
        boardPw: { // 게시글비밀번호
            type: DataTypes.STRING,
            allowNull: false,
        },
        views: { // 조회수
            type: DataTypes.INTEGER,
            defaultValue: 0,
            allowNull: true
        },
        created: {
            type: DataTypes.STRING,
            // https://gurtn.tistory.com/65
            defaultValue: new Date(+ new Date() + 3240 * 10000).toISOString().replace("T", " ").replace(/\..*/, ''),
            allowNull: true
        }
    }, {
        charset: "utf8", // 한국어 설정
        collate: "utf8_general_ci", // 한국어 설정
        modelName: 'anonyBoard', // 시퀄라이즈의 모델이름을 의미
        tableName: "anonyBoards", // 시퀄라이즈를 이용해 생성할 테이블의 이름을 의미 (모델의 복수형)
        timestamps: false, // createAt, updateAt 활성화
        paranoid: false, // deleteAt 옵션
    });

    // 조회수 증가 https://sequelize.org/docs/v6/core-concepts/model-instances/
    // 검색어로 조회 https://victorydntmd.tistory.com/92
    return anonyBoards;
};