import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080', // 기본 URL 설정
    timeout: 5000,                     // 요청 제한 시간 설정
    headers: { 'Content-Type': 'application/json' }
  });

/**
 * 로그인
 */
export const loginUser = async (username, password) => {
  const response = await apiClient.post('/auth/login', {
    username,
    password,
  });
  return response.data;
};

/**
 * 로그아웃
 */
export const logoutUser = async (username, password) => {
    const response = await apiClient.get('/auth/logout/{userId}');
      return response.data;
}