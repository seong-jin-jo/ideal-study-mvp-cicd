import React, { useState } from "react";
import RecordedLectureDetail from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureDetail";
import RecordedLectureList from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureList";
import RecordedLectureForm from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureForm";

const RecordedLecturePage = () => {
  return (
    <div>
      <h1>인강 생성 페이지</h1>
      <RecordedLectureForm />
    </div>
  );
};

export default RecordedLecturePage;
