import React from 'react';
import '../types/Types';

function UploadButton({addTranscript} : any) {

	/**
	 * Upload a transcript and send a request to the server to
	 * retrieve an analyzed transcript.
	 * @param event The event of uploading a transcript.
	 */
	const changeHandler = (event: any) => {
		const formData = new FormData();
		formData.append('transcript', event.target.files[0]);

		fetch(
			'/transcript/upload',
			{
				method: 'POST',
				body: formData,
			}
		)
		.then(response => response.json())
		.then(result => {
			addTranscript(result);
		})
		.catch((error) => {
			console.error('Error:', error);
		});

		event.target.value = null;
	};

	return (
		<div style={{ alignContent: "right" }}>
			<input type="file" name="file" onChange={changeHandler}/>
		</div>
	);
}

export default UploadButton;