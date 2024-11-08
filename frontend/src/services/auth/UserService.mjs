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
export const readUser = async (username, password) => {
    const response = await apiClient.get('/api/users/{userId}');
    return response.data;
  };

/**
 * 회원 목록 조회
 */
export const readUsers = async (username, password) => {
    const response = await apiClient.get('/api/users');
    return response.data;
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