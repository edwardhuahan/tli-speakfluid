import React from 'react';
import { Link } from 'react-router-dom';

/***ASSETS***/
import chatBot from '../assets/cb.webp'

/***COMPONENTS***/
import NavBar from '../components/NavBar';

/***STYLES***/
import '../styles/Global.css'
import '../styles/Home.css';

/**
 * The landing page where SpeakFluid's product is described.
 * @author Kai Zhuang
 * @returns The Home page as a functional component.
 */
export default function Home() {
  return (
    <div className='pageWrapper'>
      <NavBar />
      <div className='section centerContent hero'>
        <div style={{display: 'flex', flexDirection: 'row'}}>
          <div style={{width: '50%'}}>
            <img src={chatBot} alt='Illustration of a chatbot' style={{width: '100%', margin: '30px'}} />
          </div>
          <div className='centerContent' style={{margin: '30px', display: 'flex', flexDirection: 'column', width: '50%'}}>
            <h1 className='header' style={{marginBottom: '30px'}}>Your 1-click solution for classifying chatbot sentiments.</h1>
            <h2 className='subHeader' style={{marginBottom: '30px'}}>
              No need to manually classify hundreds of messages anymore. Simply upload your transcript 
              and get classified sentiments in a matter of seconds.
            </h2>
            <Link to='/analytics' style={{textDecoration: 'none', alignSelf: 'flex-start'}}>
              <div className='header hoverable' style={{padding: '17px 35px', backgroundColor: '#474af1', color: 'white', fontSize: '25px'}}>
                Get Started
              </div>
            </Link>
          </div>
        </div>
      </div>
      <div className='section'>
        
      </div>
    </div>
  )
}
