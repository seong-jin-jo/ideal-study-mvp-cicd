import React, { useState } from 'react';

const LikeButton = () => {
  const [likes, setLikes] = useState(0);

  const handleLike = () => setLikes((prev) => prev + 1);

  return (
    <button onClick={handleLike}>
      좋아요 {likes}
    </button>
  );
};

export default LikeButton;