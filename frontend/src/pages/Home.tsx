import React, { useEffect, useState } from 'react';
import '../styles/Home.css';
import '../types/HomeTypes';
import { selectedFile } from '../types/HomeTypes';

function Home() {
	const [selectedFile, setSelectedFile] = useState<any>({ name: "No file selected yet.", data: { messages: "" } });
	const [isSelected, setIsSelected] = useState(false);

	const changeHandler = (event: any) => {
		setSelectedFile(event.target.files[0]);
		setIsSelected(true);
	};

	const handleUpload = () => {
		const formData = new FormData();
		formData.append('transcript', selectedFile);

		fetch(
			'/transcript/upload',
			{
				method: 'POST',
				body: formData,
			}
		)
			.then((response) => { { response.json() } })
			.then((result) => {
				console.log('Success:', result);
			})
			.catch((error) => {
				console.error('Error:', error);
			});
	};

	return (
		<div className='home-container'>
			<input type="file" name="file" onChange={changeHandler} />
			<p>Filename: {selectedFile.name}</p>
			<div>
				<button onClick={handleUpload}>Upload</button>
			</div>
		</div>
	);
}

export default Home;