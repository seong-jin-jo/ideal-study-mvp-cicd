import apiClient from '../apiClient.mjs';

// 문의 등록
export const createInquiry = async (inquiryData) => {
    console.log("문의 등록 시도:", inquiryData);
    try {
      const response = await apiClient.post('/api/inquiries', inquiryData);
      console.log("문의 등록 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("문의 등록 실패:", err);
      throw err;
    }
  };
  
// 문의 목록 조회 (클래스별)
export const getInquiriesByClassId = async (classId) => {
    console.log(`문의 목록 조회 (클래스별) 시도: classId=${classId}`);
    try {
      const response = await apiClient.get(`/api/inquiries/classes/${classId}`);
      console.log("문의 목록 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("문의 목록 조회 실패:", err);
      throw err;
    }
  };
  
// 문의 상세 조회
export const readInquiry = async (inquiryId) => {
    console.log(`문의 상세 조회 시도: inquiryId=${inquiryId}`);
    try {
      const response = await apiClient.get(`/api/inquiries/${inquiryId}`);
      console.log("문의 상세 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("문의 상세 조회 실패:", err);
      throw err;
    }
  };
  
// 문의 수정
export const updateInquiry = async (inquiryId, inquiryData) => {
    console.log(`문의 수정 시도: inquiryId=${inquiryId}`, inquiryData);
    try {
      const response = await apiClient.patch(`/api/inquiries/${inquiryId}`, inquiryData);
      console.log("문의 수정 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("문의 수정 실패:", err);
      throw err;
    }
  };
  
// 문의 삭제
export const deleteInquiry = async (inquiryId) => {
    console.log(`문의 삭제 시도: inquiryId=${inquiryId}`);
    try {
      const response = await apiClient.delete(`/api/inquiries/${inquiryId}`);
      console.log("문의 삭제 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("문의 삭제 실패:", err);
      throw err;
    }
  };