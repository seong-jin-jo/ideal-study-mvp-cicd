import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080', // 기본 URL 설정
    timeout: 5000,                     // 요청 제한 시간 설정
    headers: { 'Content-Type': 'application/json' }
  });

/**
 * 회원 생성(가입)
 */
export const signUpUser = async (email) => {
  console.log("회원가입 API 시도:", email)
  try{
    const response = await apiClient.post('/api/users/sign-up', {
      email
    });
    console.log("회원가입 API 성공:", response)
    return response.data;
  }catch(err){
    console.error("회원가입 API 실패", err);
  }
};

/**
 * 회원 조회
 */
export const readUser = async (userId) => {
    console.log("회원조회 API 시도:", userId)
    try {

      // 실제 API 호출
      const response = await apiClient.get(`/api/users/${userId}`);
      console.log("회원조회 API 성공:", response);
      return response.data;

    } catch (error) {

      console.error('회원조회 API 실패:', error);
      
      // 디버깅
      console.log("★",userId);
      // 더미 데이터 반환
      const dummyData = {
        name : "조성진",
        phone : "010-1234-5678",
        email : "dummy@naver.com",
        school : "하버드대학교 컴퓨터공학과",
        gender : "트렌스젠더",
        grade : "1등급",
        level : 7
      };

      return dummyData;
    }
  };

/**
 * 회원 목록 조회
 */
export const readUsers = async (pathname) => {
    console.log("회원목록조회 API 시도:", pathname)
    try {
      const response = await apiClient.get('/api/users');

      console.log("회원목록조회 API 성공:", response)
      return response.data;
    } catch (error) {
      console.error('회원목록조회 API 실패:', error);
  
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
export const updateUser = async (accountData) => {
    console.error('회원수정 API 시도:', accountData);
    try{
      const response = await apiClient.post('/api/users/{userId}', {
        accountData
      });
      console.error('회원수정 API 성공:', response);

      if (response.ok) {
        alert('내용이 제출되었습니다!');
      } else {
        alert('제출 실패');
      }

    }catch(error){
      console.error('회원수정 API 실패:', error);

      const dummyData = {
        name : "조성진이다",
        password: "1234",
        phone : "010-1234-5678",
        email : "dummy@naver.com",
        school : "하버드대학교 컴퓨터공학과",
        gender : "트렌스젠더",
        grade : "1등급",
        level : 17
      }
    return dummyData;
    }
  };

/**
 * 회원 삭제
 */
export const deleteUser = async (username) => {
  console.log("회원수정 API 호출 시도:", username)
  try{
    const response = await apiClient.get('/api/users/{userId}');
    console.log("회원수정 API 호출 성공:", response)
    return response.data;
  }catch(err){
    console.log("회원수정 API 호출 실패:", err)
  }
};