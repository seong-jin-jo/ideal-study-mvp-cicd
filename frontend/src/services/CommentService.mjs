import apiClient from './apiClient.mjs'

// 댓글 등록
export const createComment = async (commentData) => {
    console.log("댓글 등록 시도:", commentData);
    try {
      const response = await apiClient.post('/api/comments', commentData);
      console.log("댓글 등록 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("댓글 등록 실패:", err);
      throw err;
    }
  };
  
// 댓글 목록 조회 (게시글별)
export const readCommentsByPostId = async (postId) => {
    console.log(`댓글 목록 조회 (게시글별) 시도: postId=${postId}`);
    try {
      const response = await apiClient.get(`/api/comments/posts/${postId}`);
      console.log("댓글 목록 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("댓글 목록 조회 실패:", err);
      throw err;
    }
  };
  
// 댓글 상세 조회
export const readComment = async (commentId) => {
    console.log(`댓글 상세 조회 시도: commentId=${commentId}`);
    try {
      const response = await apiClient.get(`/api/comments/${commentId}`);
      console.log("댓글 상세 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("댓글 상세 조회 실패:", err);
      throw err;
    }
  };
  
// 댓글 수정
export const updateComment = async (commentId, commentData) => {
    console.log(`댓글 수정 시도: commentId=${commentId}`, commentData);
    try {
      const response = await apiClient.patch(`/api/comments/${commentId}`, commentData);
      console.log("댓글 수정 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("댓글 수정 실패:", err);
      throw err;
    }
  };
  
// 댓글 삭제
export const deleteComment = async (commentId) => {
    console.log(`댓글 삭제 시도: commentId=${commentId}`);
    try {
      const response = await apiClient.delete(`/api/comments/${commentId}`);
      console.log("댓글 삭제 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("댓글 삭제 실패:", err);
      throw err;
    }
  };