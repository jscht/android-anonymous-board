'use strict';

module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.bulkInsert('anonyBoards', [
      {
        title: '첫 번째 게시글',
        subject: '이건 첫 번째 더미 게시글입니다.',
        boardPw: 'pass1234',
        views: 10,
        created: '2025-06-07 10:00:00'
      },
      {
        title: '두 번째 게시글',
        subject: '이건 두 번째 더미입니다. 길이를 좀 더 늘려볼게요.',
        boardPw: 'pass5678',
        views: 25,
        created: '2025-06-07 11:30:00'
      },
      {
        title: '세 번째 게시글',
        subject: '조회수가 0인 새 글입니다.',
        boardPw: 'secret999',
        views: 0,
        created: '2025-06-07 12:45:00'
      }
    ], {});
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete('anonyBoards', null, {});
  }
};
