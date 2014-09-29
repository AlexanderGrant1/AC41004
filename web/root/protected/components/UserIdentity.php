<?php

/**
 * UserIdentity represents the data needed to identity a user.
 * It contains the authentication method that checks if the provided
 * data can identity the user.
 */
class UserIdentity extends CUserIdentity
{
	/**
	 * Authenticates a user.
	 * The example implementation makes sure if the username and password
	 * are both 'demo'.
	 * In practical applications, this should be changed to authenticate
	 * against some persistent user identity storage (e.g. database).
	 * @return boolean whether authentication succeeds.
	 */

	private $_id;

	/**
	 * Method for authenticating the administrators.
	 */
	public function authenticate()
	{
		$user = Administrator::model()->findByAttributes(array('Username' => $this->username));

		$hashedKey = Administrator::hashPassword($this->password);

		if($user === null)
		{
			// Such user was not found.
			$this->errorCode=self::ERROR_USERNAME_INVALID;
		}
		elseif($user->LoginKey !== $hashedKey)
		{
			// The password was not corret.
			$this->errorCode=self::ERROR_PASSWORD_INVALID;	
		}
		else
		{
			// Managed to log in!
			$this->_id=$user->Id;
			Yii::app()->user->id = $user->Id;
			
			$this->errorCode=self::ERROR_NONE;
		}

		return !$this->errorCode;
	}

	  public  function getId(){
            return $this->_id;

    }
}