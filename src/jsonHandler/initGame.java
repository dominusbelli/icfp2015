package jsonHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;



public class initGame 
{
	public int ID;
	public Unit[] incoming;
	public int width;
	public int height;
	public Cell[] filled;
	public int sourceLength;
	public int[] sourceSeeds;
	
/**
 * initGame
 * @param jsonFILE
 * @throws FileNotFoundException
 * 
 * Creates a giant object called initGame from JSON file
 * and creates an object readable by Java to generate the game
 */
	public initGame(File jsonFILE) throws FileNotFoundException
	{
		/*
		 * This tries to decipher the json file
		 * Takes the JSON File, turns it into a String,
		 * then turns it into a JSONObject called json
		 */
		BufferedReader br = new BufferedReader(new FileReader(jsonFILE));
		StringBuilder sb = new StringBuilder();
		String line = "";
		try 
		{
			while((line = br.readLine()) != null)
			{
				sb.append(line);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		JSONObject json = new 
				JSONObject(sb.toString());
		
		/*
		 * These lines get values from the JSONObject and put them into
		 * the initGame object
		 */
		this.ID = json.getInt("id");
		this.width = json.getInt("width");
		this.height = json.getInt("height");
		this.sourceLength = json.getInt("sourceLength");
		/*
		 * This block parses the Array of Units that spawn
		 * They parse the Unit Array and puts the Members Array into a JSONArray
		 * and the Pivot into a JSONObject
		 */
		JSONArray jsonUnitArray = json.getJSONArray("units");
		this.incoming = new Unit[jsonUnitArray.length()];
		for(int memberIter = 0; memberIter <= jsonUnitArray.length()-1; memberIter++)
		{
			/*
			this.incoming[iter].members[iter].x = jsonUnitArray.getJSONObject(iter).getInt("x");
			this.incoming[iter].members[iter].y = jsonUnitArray.getJSONObject(iter).getInt("y");
			this.incoming[iter].pivot.x = jsonUnitArray.getJSONObject(iter).getInt("x");
			this.incoming[iter].pivot.y = jsonUnitArray.getJSONObject(iter).getInt("y");
			*/
			JSONObject jsonUnit = jsonUnitArray.getJSONObject(memberIter);
			JSONArray jsonMemberArray = jsonUnit.getJSONArray("members");
			JSONObject jsonPivot = jsonUnit.getJSONObject("pivot");
			
			/*System.out.println("Keys: " + jsonUnit.keySet());
			System.out.println("PivotKeys: " + jsonPivot.keySet());
			System.out.println("Pivot: (" + jsonPivot.getInt("x") + "," + jsonPivot.getInt("y") + ")");*/
			
			this.incoming[memberIter] = new Unit();
			this.incoming[memberIter].members = new Cell[jsonMemberArray.length()];
			for(int cellIter = 0; cellIter <= jsonMemberArray.length()-1; cellIter++)
			{
				this.incoming[memberIter].members[cellIter] = new Cell();
				this.incoming[memberIter].members[cellIter].x = jsonMemberArray.getJSONObject(cellIter).getInt("x");
				this.incoming[memberIter].members[cellIter].y = jsonMemberArray.getJSONObject(cellIter).getInt("y");
			}
			this.incoming[memberIter].pivot = new Cell();
			this.incoming[memberIter].pivot.x = jsonPivot.getInt("x");
			this.incoming[memberIter].pivot.y = jsonPivot.getInt("y");
		}
		/*
		 * This block turns the FilledArray into a JSONArray
		 * then puts the x and y values from the JSONArray into the filled array in initGame
		 */
		JSONArray jsonFilledArray = json.getJSONArray("filled");
		this.filled = new Cell[jsonFilledArray.length()];
		for(int fillIter = 0; fillIter <= jsonFilledArray.length()-1; fillIter++)
		{
			JSONObject jsonFilledCell = jsonFilledArray.getJSONObject(fillIter);
			this.filled[fillIter].x = jsonFilledCell.getInt("x");
			this.filled[fillIter].y = jsonFilledCell.getInt("y");
		}
		/*
		 * This parses JSON to a sourceSeeds Int Array
		 */
		JSONArray jsonSourceSeeds = json.getJSONArray("sourceSeeds");
		this.sourceSeeds = new int[jsonSourceSeeds.length()];
		for(int sourceSeedsIter = 0; sourceSeedsIter <= jsonSourceSeeds.length()-1; sourceSeedsIter++)
		{
			//System.out.println("SourceSeeds: " + jsonSourceSeeds.optInt(sourceSeedsIter));
			this.sourceSeeds[sourceSeedsIter] = jsonSourceSeeds.optInt(sourceSeedsIter);
		}
	}
}
