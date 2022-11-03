import React, { useEffect, useState } from 'react';
import '../types/HomeTypes';
import { selectedFile } from '../types/HomeTypes';
import Button from '@mui/material/Button';

function UploadButton() {
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
		<div style={{ alignContent: "right" }}>
			<input type="file" name="file" onChange={changeHandler} />
			{/* <p>Filename: {selectedFile.name}</p> */}
			<button onClick={handleUpload} >
				Upload
			</ button>
		</div>
	);
}

export default UploadButton;