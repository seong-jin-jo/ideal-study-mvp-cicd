import React, { useState } from "react";
import LectureItem from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureItem";
import LectureDetail from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureDetail";
import LectureList from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureList";
import LectureForm from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureForm";
import VimeoUploader from "../../../../components/vimeo/VimeoUploader";
import VimeoPlayer from "../../../../components/vimeo/VimeoPlayer";

const LecturePage = () => {
  const [videos, setVideos] = useState([]);
  const [currentVideo, setCurrentVideo] = useState(null);

  const handleUpload = (newVideo) => {
    setVideos([...videos, newVideo]);
  };

  return (
    <div>
      <h1>Vimeo Page</h1>
      <LectureForm />
      <LectureList
        lectures={[
          {
            id: "1",
            title: "제목",
            description: "설명",
            video: "비디오",
          },
          {
            id: "2",
            title: "제목2",
            description: "설명2",
            video: "비디오2",
          },
        ]}
      />
      <LectureDetail />
      <LectureItem />
      <VimeoUploader onUpload={handleUpload} />
      <VimeoPlayer videoUrl={currentVideo} />
    </div>
  );
};

export default LecturePage;
