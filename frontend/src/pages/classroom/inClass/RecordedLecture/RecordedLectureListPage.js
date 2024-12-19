import React, { useEffect, useState } from "react";
import RecordedLectureList from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureList";
import { readRecordedLectures } from "../../../../services/classroom/RecordedLectureService.mjs";
import Button from "../../../../components/Button";
import { useNavigate } from "react-router-dom";

const RecordedLectureListPage = () => {
  const [lectures, setLectures] = useState([]);
  const navigate = useNavigate();
  const isLoggedIn = true; // TODO: 실제 로그인 상태 확인 로직으로 교체
  const [expandedLectureId, setExpandedLectureId] = useState(null); // 클릭시 펼칠 강의

  useEffect(() => {
    const fetchLectures = async () => {
      const lectures = await readRecordedLectures();
      setLectures(lectures.data);
    };
    fetchLectures();
  }, []);

  const handleLectureExpand = (lectureId) => {
    if (isLoggedIn) {
      setExpandedLectureId(lectureId);
    }
    // 비회원이면 아무 동작 안 함
  };

  return (
    <div>
      <h2> 인강 목록 </h2>
      {isLoggedIn && (
        <Button onClick={() => navigate("/recordedLecture/new")}>
          인강 생성
        </Button>
      )}
      <RecordedLectureList
        lectures={lectures}
        onLectureClick={handleLectureExpand} // 클릭시 동작할 함수 (자식에서 클릭함)
        expandedLectureId={expandedLectureId} // 클릭시 펼칠 강의 (자식에서 클릭할 강의)
        isClickable={isLoggedIn} // 비회원이면 클릭 불가능
      />
    </div>
  );
};

export default RecordedLectureListPage;
