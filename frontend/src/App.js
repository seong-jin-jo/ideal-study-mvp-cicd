import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import HomePage from "./pages/home/HomePage";
import SignUpPage from "./pages/auth/SignUpPage";
import SignUpCompletePage from "./pages/auth/SignUpCompletePage";
import LoginPage from "./pages/auth/LoginPage";

import Header from "./components/Header";
import Footer from "./components/Footer";
import ProfileListPage from "./pages/user/ProfileListPage";
import ProfilePage from "./pages/user/ProfilePage";
import NotFound from "./pages/NotFound";
import OfficialProfilePage from "./pages/teacher/OfficialProfilePage";
import OfficialProfilePageUpdate from "./pages/teacher/OfficialProfilePageUpdate";

import "./App.css"; // 스타일 파일 import
import Sidebar from "./components/Sidebar";
import ClassroomListPage from "./pages/classroom/ClassroomListPage";
import ClassroomPage from "./pages/classroom/ClassroomPage";
import InquiyBoard from "./pages/classroom/preClass/inquiry/InquiryBoardPage";
import InquiryForm from "./components/classroom/preClass/inquiry/InquiryForm";
import InquiryDetailPage from "./pages/classroom/preClass/inquiry/InquiryDetailPage";
import ClassroomForm from "./components/classroom/ClassroomForm";
import RecordedLecturePage from "./pages/classroom/inClass/recordedLecture/RecordedLecturePage";
import RecordedLectureListPage from "./pages/classroom/inClass/recordedLecture/RecordedLectureListPage";
import RecordedLectureDetailPage from "./pages/classroom/inClass/recordedLecture/RecordedLectureDetailPage";

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Header />
        <div className="page-common">
          <Sidebar />
          <div className="page-content">
            <Routes>
              {/* 인강 업로드 */}
              <Route
                path="/recordedLecture/new"
                element={<RecordedLecturePage />}
              />
              {/* 인강 목록 */}
              <Route
                path="/recordedLecture/list/:classId"
                element={<RecordedLectureListPage />}
              />
              {/* 인강 상세 */}
              <Route
                path="/recordedLecture/:id"
                element={<RecordedLectureDetailPage />}
              />
              {/* main */}
              <Route path="/" element={<HomePage />} />
              {/* auth */}
              <Route path="/signup" element={<SignUpPage />} />
              <Route path="/signup-complete" element={<SignUpCompletePage />} />
              <Route path="/login" element={<LoginPage />} />
              {/* user */}
              <Route path="/teachers" element={<ProfileListPage />} />
              <Route path="/students" element={<ProfileListPage />} />
              <Route path="/myPage/:id" element={<ProfilePage />} />
              {/* teachers only */}
              <Route
                path="/officialPage/:id"
                element={<OfficialProfilePage />}
              />
              <Route
                path="/officialPageUpdate"
                element={<OfficialProfilePageUpdate />}
              />

              {/* classroom */}
              <Route path="/classes" element={<ClassroomListPage />} />
              <Route path="/classes/:classId" element={<ClassroomPage />} />

              {/* 클래스 목록 */}
              <Route path="/classes" element={<ClassroomListPage />} />
              {/* 클래스 생성 */}
              <Route path="/classes/new" element={<ClassroomForm />} />
              {/* 클래스 수정 */}
              <Route
                path="/classes/:classId/edit"
                element={<ClassroomForm />}
              />
              {/* 클래스 상세 */}
              <Route path="/classes/:classId" element={<ClassroomPage />} />

              {/* classroom - inquiry */}
              {/* 문의 목록 */}
              <Route
                path="/classes/:classId/inquiries"
                element={<InquiyBoard />}
              />
              {/* 문의 작성 */}
              <Route
                path="/classes/:classId/inquiries/new"
                element={<InquiryForm />}
              />
              {/* 문의 상세 */}
              <Route
                path="/classes/:classId/inquiries/:inquiryId"
                element={<InquiryDetailPage />}
              />
              {/* 문의 수정 */}
              <Route
                path="/classes/:classId/inquiries/:inquiryId/edit"
                element={<InquiryForm />}
              />
              {/* 일치하는 라우트가 없을 때 NotFound 컴포넌트 렌더링 */}
              <Route path="*" element={<NotFound />} />
            </Routes>
          </div>
        </div>
        <Footer />
      </Router>
    </AuthProvider>
  );
};

export default App;
