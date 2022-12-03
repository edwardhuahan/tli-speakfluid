import React from 'react';

/***STYLES***/
import '../styles/Global.css';
import '../styles/components/UploadButton.css';

/***TYPES***/
import '../types/Types';

/**
 * The functional component for the button to upload transcripts for analyzing.
 * @author Kai Zhuang
 * @param addTranscript The function to change the transcripts state in the Analytics page.
 * @returns A react functional component for the UploadButton.
 */
function UploadButton({addTranscript} : any) {

	/**
	 * Upload a transcript and send a request to the server to
	 * retrieve an analyzed transcript.
	 * @author Kai Zhuang
	 * @param event The event of uploading a transcript.
	 */
	const changeHandler = (event: any): void => {
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
		<div style={{marginBottom: '20px', width: '100%'}}>
			<label htmlFor='fileUpload' className='uploadButton header hoverable'>
				<i></i>Upload Transcript
			</label>
			<input id='fileUpload' type='file' name='file' onChange={changeHandler} style={{display: 'none'}} accept='.json'/>
		</div>
	);
}

export default UploadButton;