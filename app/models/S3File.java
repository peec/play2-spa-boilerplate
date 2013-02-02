package models;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import controllers.routes;
import play.Logger;
import play.Play;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import plugins.S3Plugin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;


/**
 * Wrapper around S3File.
 * Supports storing files locally or on Amazon S3.
 * If S3 is configured it will store file on Amazon S3 automatically.
 * 
 * This means that local development via plays "run" command will not use S3 in development, but if heroku is configured to use 
 * configuration keys files is accessed @ S3 storage.
 * 
 * Custom file objects can ManyToMany to this file or create a custom file object that uses ManyToOne against this.
 * 
 * @author Petter Kjelkenes <kjelkenes@gmail.com>
 *
 */
@Entity
public class S3File extends Model {

    @Id
    public UUID id;

    private String bucket;

    protected String name;
    
    @Transient
    protected File file;

	public static Finder<Long,S3File> find = new Finder<Long,S3File>(Long.class,S3File.class);

    public S3File(){}
    
    /**
     * 
     * @param file The file to be uploaded.
     * @param fileName The filename of the file to be uploaded.
     */
    public S3File(File file, String fileName){
    	setFile(file);
    	setName(fileName);
    }
    
    
    

    /**
     * Gets the file URL of existing file.
     * @return
     * @throws MalformedURLException
     */
    @JsonProperty
    public String getUrl(){
    	if (S3Plugin.amazonS3 != null) {
    		return "https://s3.amazonaws.com/" + bucket + "/" + getActualFileName();
    	}else{
    		return routes.Assets.at("upload/" + getActualFileName()).url();
    	}
    }
    
    @JsonIgnore
    public void setUrl(String u){
    	// Do nothing. For json. Don't remove, this is so that it's allowed to try setting url.
    	// JSON parser will use this method.
    }
    
    
    
    
    
    

    @JsonIgnore
    public File fetchRealFile(){
    	File f = null;
    	if (S3Plugin.amazonS3 != null) {
    		
    		try {
    			f = File.createTempFile("tmp_s3_dl_" + id + "_" + System.nanoTime(), "." + FilenameUtils.getExtension(getName()));
				FileUtils.copyURLToFile(new URL(getUrl()), f);
			} catch (MalformedURLException e) {
				Logger.error("URL is not valid to create temp file from amazon.", e);
			} catch (IOException e) {
				Logger.error("Unable to create temp file from amazon.", e);
			}
    	}else{
    		f = getDevelopmentFile();
    	}
    	return f;
    }
    
    
    /**
     * Returns the actual file name.
     * @return
     */
    @JsonIgnore
    public String getActualFileName() {
        return id + "/" + name;
    }
    
    @JsonIgnore
    private File getDevelopmentFile(){
    	return new File(Play.application().path().getAbsolutePath() + "/public/upload/" + getActualFileName());
    }
    @JsonIgnore
    private File getDevelopmentFolder(){
    	return new File(Play.application().path().getAbsolutePath() + "/public/upload/" + id);
    }
    

    
    @Override
    public void save() {
        if (S3Plugin.amazonS3 == null) {
        		try {
        			super.save(); // assigns an id
	        		
        			File dir = getDevelopmentFolder();
        			if (!dir.exists())dir.mkdir();

        			
					FileUtils.moveFile(file, getDevelopmentFile());

				} catch (IOException e) {
					Logger.error("Could not store upload file in dev.", e);
				}
        }
        else {
            this.bucket = S3Plugin.s3Bucket;
            
            super.save(); // assigns an id

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, getActualFileName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    @Override
    public void delete() {
        if (S3Plugin.amazonS3 == null) {
        	
        	// In development delete from upload folder.
        	getDevelopmentFile().delete();
        	getDevelopmentFolder().delete();
        	
        }
        else {
            S3Plugin.amazonS3.deleteObject(bucket, getActualFileName());
            super.delete();
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replace(' ', '-'); // Don't allow spaces in filenames.
	}

	@JsonIgnore
	public File getFile(){
		return file;
	}
	@JsonIgnore
	public void setFile(File file){
		this.file = file;
	}
	
	static public S3File getById(UUID id){
		return find.where().eq("id",id).findUnique();
	}
	
}
