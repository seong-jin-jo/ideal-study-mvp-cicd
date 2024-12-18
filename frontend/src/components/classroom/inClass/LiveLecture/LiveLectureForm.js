import React, { useState } from "react";
import { createLiveLecture } from "../../../../services/classroom/LiveLectureService.mjs";

const LiveLectureForm = ({ classId }) => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    startDate: "",
    duration: "",
    platform: "",
    location: "",
    link: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endTime = new Date(formData.startDate);
    endTime.setMinutes(endTime.getMinutes() + formData.duration);

    const lectureData = {
      ...formData,
      classId,
      startTime: formData.startDate.toISOString(),
      endTime: endTime.toISOString(),
    };

    await createLiveLecture(lectureData);
  };

  return (
    <form onSubmit={handleSubmit} className="liveLectureForm">
      <input type="text" name="title" placeholder="제목" />
      <input type="text" name="description" placeholder="설명" />
      <input type="text" name="startDate" placeholder="시작일" />
      <input type="number" name="duration" placeholder="소요시간 (분)" />
      <select>
        <option value="ZOOM">ZOOM</option>
        <option value="GOOGLE MEET">GOOGLE MEET</option>
        <option value="오프라인">스터디카페</option>
      </select>
      <input type="text" name="location" placeholder="장소" />
      <input type="url" name="link" placeholder="수업 URL" />
      <button type="submit">생성</button>
      <button type="button">취소</button>
    </form>
  );
};

export default LiveLectureForm;
