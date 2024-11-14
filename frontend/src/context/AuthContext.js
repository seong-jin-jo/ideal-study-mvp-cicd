import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false); // 로그인 여부 상태

  const login = () => setIsAuthenticated(true); // 로그인 시 호출
  const logout = () => setIsAuthenticated(false); // 로그아웃 시 호출

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};