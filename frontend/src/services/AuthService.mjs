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
    console.log("로그인 API 시도:", username, password)
    try{
      const response = await apiClient.post('/auth/login', {
        username,
        password,
      });
      console.log("로그인 API 성공:", response)
      return response.data;
    }catch(error){
      console.log("로그인 API 실패",error);
        
      // 로그인실패시 임시로 더미 데이터 반환
      const dummyData = { id: 1, name: '최예원', role: 'student', level: '3' }
  
      return dummyData;
    }
};

/**
 * 로그아웃
 */
export const logoutUser = async (username, password) => {
    console.log("로그아웃 API 시도:", username, password)
    try{
      const response = await apiClient.get('/auth/logout/{userId}');
      console.log("로그아웃 API 성공:", response)
      return response.data;
    }catch(err){
      console.log("로그아웃 API 실패:", err)
    }
}