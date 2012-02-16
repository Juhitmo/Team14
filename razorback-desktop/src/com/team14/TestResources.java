package com.team14;

import static org.junit.Assert.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.junit.Test;

public class TestResources {

	@Test
	public void test() {
		FileHandle handle = null;
		
		try
		{
			handle = Gdx.files.internal("assets");
		}
		catch (NullPointerException npe)
		{
		}
		

		assertSame(null, handle);
	}

}
