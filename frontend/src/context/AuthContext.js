import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false); // 로그인 여부 상태
  const [userInfo, setUserInfo] = useState({ name: '', id: '', level: '', role: '' }); // 유저 정보 상태
  
   // 로그인 시 호출, 유저 정보 설정
   const login = ({name, id, level, role}) => {
    setIsAuthenticated(true);
    setUserInfo({ name, id, level, role });
  };

  // 로그아웃 시 호출, 유저 정보 초기화
  const logout = () => {
    setIsAuthenticated(false);
    setUserInfo({ name: '', id: '', level: '', role: '' });
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, userInfo, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};