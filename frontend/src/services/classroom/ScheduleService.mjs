import apiClient from '../apiClient.mjs';

// 스케줄러 일정 생성 API
export const createSchedule = async (classId, scheduleData) => {
    console.log("스케줄러 일정 생성 API 시도:", { classId, scheduleData });
    try {
      const response = await apiClient.post(`/api/classes/${classId}/schedules`, scheduleData);
      console.log("스케줄러 일정 생성 API 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("스케줄러 일정 생성 API 실패", err);
      throw err;
    }
  };
  

// 스케줄러 목록 조회 API (클래스별)
export const getSchedulesByClass = async (classId) => {
    console.log("스케줄러 목록 조회 API 시도:", { classId });
    try {
      const response = await apiClient.get(`/api/schedules/classes/${classId}`);
      console.log("스케줄러 목록 조회 API 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("스케줄러 목록 조회 API 실패", err);
      throw err;
    }
  };

// 스케줄 상세 조회
export const getScheduleDetails = async (scheduleId) => {
  console.log(`스케줄 상세 조회 시도: scheduleId=${scheduleId}`);
  try {
    const response = await apiClient.get(`/api/schedules/${scheduleId}`);
    console.log("스케줄 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄 상세 조회 실패:", err);
    throw err;
  }
};

// 스케줄 수정
export const updateSchedule = async (scheduleId, scheduleData) => {
  console.log(`스케줄 수정 시도: scheduleId=${scheduleId}`, scheduleData);
  try {
    const response = await apiClient.patch(`/api/schedules/${scheduleId}`, scheduleData);
    console.log("스케줄 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄 수정 실패:", err);
    throw err;
  }
};

// 스케줄 삭제
export const deleteSchedule = async (scheduleId) => {
  console.log(`스케줄 삭제 시도: scheduleId=${scheduleId}`);
  try {
    const response = await apiClient.delete(`/api/schedules/${scheduleId}`);
    console.log("스케줄 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄 삭제 실패:", err);
    throw err;
  }
};