import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080', // 기본 URL 설정
    timeout: 5000,                     // 요청 제한 시간 설정
    headers: { 'Content-Type': 'application/json' }
  });

/**
 * 회원 생성(가입)
 */
export const signUpUser = async (username, password) => {
  const response = await apiClient.post('/api/users/sign-up', {
    username,
    password,
  });
  return response.data;
};

/**
 * 회원 조회
 */
export const readUser = async (userId) => {
    try {

      // 실제 API 호출
      const response = await apiClient.get(`/api/users/${userId}`);
      return response.data;

    } catch (error) {

      console.error('회원조회 API 호출 오류 발생:', error);
      
      // 디버깅
      console.log("★",userId);
      // 더미 데이터 반환
      const dummyData = {
        id: userId,
        name: userId === 1 ? '김대민' : 'Unknown',
        role: userId === 1 ? '학생' : 'student',
        level: 17
      };

      return dummyData;
    }
  };

/**
 * 회원 목록 조회
 */
export const readUsers = async (pathname) => {
    try {
      const response = await apiClient.get('/api/users');
      return response.data;
    } catch (error) {
      console.error('회원목록조회 API 호출 오류 발생:', error);
  
      // 경로에 따라 더미 데이터 반환
      const dummyData = pathname === '/teachers'
        ? [
            { id: 1, name: 'Alice', role: 'teacher' },
            { id: 3, name: 'Charlie', role: 'teacher' }
          ]
        : [
            { id: 2, name: 'Bob', role: 'student' },
            { id: 4, name: 'David', role: 'student' }
          ];
  
      return dummyData;
    }
  };

/**
 * 회원 수정(업데이트)
 */
export const updateUser = async (username, password) => {
    const response = await apiClient.get('/api/users/{userId}');
    return response.data;
  };

/**
 * 회원 삭제
 */
export const deleteUser = async (username, password) => {
    const response = await apiClient.get('/api/users/{userId}');
    return response.data;
  };