var express = require("express");
var router = express.Router();
let model = require("../models");

/* 전체 게시글 조회 */
router.get("/board", async (req, res) => {
  // 글번호, 제목, 등록일시, 조회수
  // 기본 최신순, 조회순 정렬
  let posts = [];

  // 사용자가 입력한 키워드를 제목에 포함한 게시글 리스트 조회 가능
  if (req.query.keyword == undefined || req.query.keyword == '') {
    // 키워드가 입력되지 않았을 때
    posts = await model.anonyBoard.findAll();

    if (posts.legnth != 0){
      if(req.query.order == "views"){
        posts = await model.anonyBoard.findAll({
          order: [["views", "asc"]]
        });
      } else {
        posts = await model.anonyBoard.findAll({
          order: [["created", "desc"]] // id asc 순으로 해도 됨
        });
      }

    } else return res.send("조회된 게시글이 존재하지 않습니다.")

  } else {
    // 키워드가 입력되었을 때
    posts = await model.anonyBoard.findAll();

    if (posts.legnth != 0){
      if(req.query.order == "views"){
        posts = await model.anonyBoard.findAll({
          where: { title: req.query.keyword },
          order: [["views", "asc"]]
        });
      } else {
        posts = await model.anonyBoard.findAll({
          where: { title: req.query.keyword },
          order: [["created", "desc"]]
        });
      }

    } else return res.send("조회된 게시글이 존재하지 않습니다.")

  }

  res.json(posts);

});

/* 게시글 검색 */
router.get("/board/:id", async(req, res) => {
  let findPost = await model.anonyBoard.findOne({
    where: { id: req.params.id }
  });

  let postViews = await model.anonyBoard.update(
    { views: findPost.views + 1 },
    { where: { id: findPost.id } }
  );

  let post = await model.anonyBoard.findOne({
    where: { id: findPost.id }
  });

  if(post != null) res.json(post)
  else res.send("게시글이 삭제되었거나 없습니다.");
});

/* 게시글 등록 */
router.post("/regist", async(req, res) => {
  // 제목, 본문, 비밀번호

  // console.log(req.body)
  let posting = await model.anonyBoard.create({
    title: req.body.title,
    subject: req.body.subject,
    boardPw: req.body.boardPw
  })
  // console.log(posting)

  if(posting != null) res.send("게시글이 정상적으로 등록되었습니다.");
  else res.send("게시글 등록에 실패했습니다.");
});

/* 게시글 수정 정보 */
router.get("/revise/:postId", async(req, res) => {
  // 비밀 번호가 맞으면 제목, 본문 수정 가능
  // 게시글이 수정되어도 조회수는 변하지 않음.
  let post = await model.anonyBoard.findOne({
    where: { id: parseInt(req.params.postId) }
  });

  res.json({
    id: post.id,
    title: post.title,
    subject: post.subject,
  })

})

/* 게시글 수정 */
router.put("/revise", async(req, res) => {
  // 입력받은 정보(id 제외)
  let data = {
    id: req.body.id,
    title: req.body.title,
    subject: req.body.subject,
    password: req.body.password.toString()
  }

  let postId = await model.anonyBoard.findOne({ where: {id: data.id} })
  let pwChk = postId.boardPw.toString()

  if(data.password === pwChk){
    // 수정 로직
    const result = await model.anonyBoard.update(
      { title: data.title, subject: data.subject },
      { where: { id: data.id } }
    );
    
    if(result != null) res.send("게시글 내용이 정상적으로 수정되었습니다.")
    else res.send("게시글 내용을 수정하는데 문제가 발생하였습니다.")

  } else res.send("비밀번호가 일치하지 않습니다.")

});

/* 게시글 삭제 */
router.delete("/delete", async(req, res) => {
  // 비밀번호가 맞으면 게시글 삭제
console.log(req.params)
  // 입력받은 정보(id 제외)
  let data = {
    id: req.body.id,
    password: req.body.password
  }

  let postId = await model.anonyBoard.findOne({ where: {id: data.id} })
  let pwChk = postId.boardPw.toString()

  if(data.password === pwChk){
    // 삭제 로직
    const result = await model.anonyBoard.destroy({ 
      where: { id: data.id }
    });
    
    if(result != null) res.send("게시글이 정상적으로 삭제되었습니다.")
    else res.send("게시글을 삭제하는데 문제가 발생하였습니다.")

  } else res.send("비밀번호가 일치하지 않습니다.")

});

module.exports = router;