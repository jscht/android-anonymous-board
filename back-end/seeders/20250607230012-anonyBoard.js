'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    return queryInterface.bulkInsert('anonyBoards', [
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
      },
      {
        title: '네 번째 게시글',
        subject: '비밀번호 테스트용 게시글입니다.',
        boardPw: 'pwtest',
        views: 3,
        created: '2025-06-07 13:15:00'
      },
      {
        title: '다섯 번째 게시글',
        subject: '이 게시글은 꽤 긴 본문을 포함하고 있습니다. 다양한 텍스트가 들어갈 수 있도록 작성되었어요.',
        boardPw: 'longbody',
        views: 17,
        created: '2025-06-07 14:20:00'
      },
      {
        title: '여섯 번째 게시글',
        subject: '비어 있는 듯하지만 사실은 그렇지 않은 게시글입니다.',
        boardPw: 'hiddenmsg',
        views: 1,
        created: '2025-06-07 15:00:00'
      },
      {
        title: '일곱 번째 게시글',
        subject: '다시 작성한 게시글입니다. 수정 테스트용으로 사용하세요.',
        boardPw: 'editmode',
        views: 9,
        created: '2025-06-07 15:45:00'
      },
      {
        title: '여덟 번째 게시글',
        subject: '오타를 일부러 넣은 게시글입니다. 오타 수정 연습하세요.',
        boardPw: 'typo123',
        views: 4,
        created: '2025-06-07 16:30:00'
      },
      {
        title: '아홉 번째 게시글',
        subject: '오늘의 급식은 무엇일까요?',
        boardPw: 'lunchpwd',
        views: 20,
        created: '2025-06-07 17:05:00'
      },
      {
        title: '열 번째 게시글',
        subject: '시더용 마지막 게시글입니다. 모두 수고하셨습니다.',
        boardPw: 'finale10',
        views: 30,
        created: '2025-06-07 18:00:00'
      }
    ], {});
  },

  down: async (queryInterface, Sequelize) => {
    return queryInterface.bulkDelete('anonyBoards', null, {});
  }
};
