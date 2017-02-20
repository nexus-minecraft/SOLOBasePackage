package solo.solobasepackage.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import cn.nukkit.Server;
import cn.nukkit.utils.Utils;

@Deprecated
@SuppressWarnings("serial")
public class Database<K, V> extends LinkedHashMap<K, V>{
	
	public static final File DEFAULT_DATA_PATH = new File(Server.getInstance().getDataPath() + File.separator + "pluginsData");

	public final Class<K> keyClazz;
	public final Class<V> valueClazz;
	
	public final File file;
	
	public Database(Class<K> keyClazz, Class<V> valueClazz, File dataFolder, String fileName){
		this(keyClazz, valueClazz, dataFolder, fileName, new LinkedHashMap<K, V>());
	}
	
	public Database(Class<K> keyClazz, Class<V> valueClazz, File dataFolder, String fileName, LinkedHashMap<K, V> defaultValues){
		this(keyClazz, valueClazz, new File(dataFolder, fileName), defaultValues);
	}
	
	public Database(Class<K> keyClazz, Class<V> valueClazz, File dataFile){
		this(keyClazz, valueClazz, dataFile, new LinkedHashMap<K, V>());
	}
	
	public Database(Class<K> keyClazz, Class<V> valueClazz, File dataFile, LinkedHashMap<K, V> defaultValues){
		super(defaultValues);
		
		this.keyClazz = keyClazz;
		this.valueClazz = valueClazz;
		this.file = dataFile;
		
		if(! this.file.isFile()){
			try{
				this.file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		this.load();
	}
	
	public void load(){
		Gson gson = new GsonBuilder().create();
		JsonReader reader = null;
		try{
			reader = new JsonReader(new FileReader(this.file));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		Type collectionType = new TypeToken<LinkedHashMap<K, V>>() {}.getType();
		LinkedHashMap<K, V> data = gson.fromJson(reader, collectionType);
		if(data == null){
			return;
		}
		this.putAll(data);
	}
	
	public void save(){
		Gson gson = new GsonBuilder().create();
		Writer writer = null;
		try {
			writer = new FileWriter(this.file);
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			Utils.writeFile(this.file, gson.toJson((LinkedHashMap<K, V>) this));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}