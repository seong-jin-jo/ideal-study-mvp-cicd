import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import HomePage from './pages/home/HomePage'
import SignUpPage from './pages/auth/SignUpPage'
import SignUpCompletePage from './pages/auth/SignUpCompletePage'
import LoginPage from './pages/auth/LoginPage'

import Header from './components/Header';
import Footer from './components/Footer';

const App = () => {
  return (
    <AuthProvider>
      <Router>
      <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/signup-complete" element={<SignUpCompletePage />} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </Router>
      <Footer />
    </AuthProvider>
  );
};

export default App;
