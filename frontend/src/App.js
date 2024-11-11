import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import HomePage from './pages/home/HomePage'
import SignUpPage from './pages/auth/SignUpPage'
import SignUpCompletePage from './pages/auth/SignUpCompletePage'
import LoginPage from './pages/auth/LoginPage'

import Header from './components/Header';
import Footer from './components/Footer';
import ProfileListPage from './pages/user/ProfileListPage';
import ProfilePage from './pages/user/ProfilePage';

const App = () => {
  return (
    <AuthProvider>
      <Router>
      <Header />
        <Routes>
          {/* main */}
          <Route path="/" element={<HomePage />} />
          {/* auth */}
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/signup-complete" element={<SignUpCompletePage />} />
          <Route path="/login" element={<LoginPage />} />
          {/* user */}
          <Route path="/teachers" element={<ProfileListPage />} />
          <Route path="/students" element={<ProfileListPage />} />
          <Route path="/profile/:id" element={<ProfilePage/>} />
        </Routes>
      </Router>
      <Footer />
    </AuthProvider>
  );
};

export default App;
