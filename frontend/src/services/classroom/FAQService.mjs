import apiClient from '../apiClient.mjs';

// F&Q 등록
export const createFAQ = async (faqData) => {
    console.log("F&Q 등록 시도:", faqData);
    try {
      const response = await apiClient.post('/api/faqs', faqData);
      console.log("F&Q 등록 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("F&Q 등록 실패:", err);
      throw err;
    }
  };
  
// F&Q 목록 조회 (클래스별)
export const readFAQsByClassId = async (classId) => {
    console.log(`F&Q 목록 조회 (클래스별) 시도: classId=${classId}`);
    try {
      const response = await apiClient.get(`/api/faqs/classes/${classId}`);
      console.log("F&Q 목록 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("F&Q 목록 조회 실패:", err);
      throw err;
    }
  };
  
// F&Q 상세 조회
export const readFAQ = async (faqId) => {
    console.log(`F&Q 상세 조회 시도: faqId=${faqId}`);
    try {
      const response = await apiClient.get(`/api/faqs/${faqId}`);
      console.log("F&Q 상세 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("F&Q 상세 조회 실패:", err);
      throw err;
    }
  };
  
// F&Q 수정
export const updateFAQ = async (faqId, faqData) => {
    console.log(`F&Q 수정 시도: faqId=${faqId}`, faqData);
    try {
      const response = await apiClient.patch(`/api/faqs/${faqId}`, faqData);
      console.log("F&Q 수정 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("F&Q 수정 실패:", err);
      throw err;
    }
  };
  
// F&Q 삭제
export const deleteFAQ = async (faqId) => {
    console.log(`F&Q 삭제 시도: faqId=${faqId}`);
    try {
      const response = await apiClient.delete(`/api/faqs/${faqId}`);
      console.log("F&Q 삭제 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("F&Q 삭제 실패:", err);
      throw err;
    }
  };