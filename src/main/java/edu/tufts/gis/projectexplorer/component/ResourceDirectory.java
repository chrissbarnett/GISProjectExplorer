package edu.tufts.gis.projectexplorer.component;

import edu.tufts.gis.projectexplorer.domain.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;



/**
 * Created by cbarne02 on 5/1/15.
 *
 * Class for working with project directory and associated resources
 */
@Component
public class ResourceDirectory {

    @Value("${data_directory.resource_directory}")
    protected String managedDir;

    @Value("${data_directory.path}")
    private String dataDir;

    private Path dataDirectory;
    private Path managedDirectory;

    private static final Logger log = LoggerFactory.getLogger(ResourceDirectory.class);

    @PostConstruct
    public void init() throws IOException {
        try {
            log.info(dataDir);
            dataDirectory = getDirectory(dataDir);
            managedDirectory = getDirectory(dataDirectory, managedDir);
        } catch (IOException e){
            log.error("Unable to create or retrieve resource directory!!!");
            throw e;
        }
    }

    /**
     * moves the file "resourcePath" from current location to the resource directory
     *
     * @param resourcePath
     * @param resourceId
     */
    public Path addResource(UUID resourceId, ResourceType resourceType, Path resourcePath) throws IOException {

        Path targetDir = getDirectory(managedDirectory, resourceId.toString());
        Path targetFile = Paths.get(targetDir.toString(), resourceType.getResourceName());
        Files.move(resourcePath, targetFile, REPLACE_EXISTING, ATOMIC_MOVE);
        return targetFile;
    }

    public Path addResource(UUID resourceId, ResourceType resourceType, MultipartFile multipartFile) throws IOException {
        Path targetDir = getDirectory(managedDirectory, resourceId.toString());
        Path targetFile = Paths.get(targetDir.toString(), resourceType.getResourceName());
        multipartFile.transferTo(targetFile.toFile());
        return targetFile;
    }

    public boolean removeResource(UUID resourceId, ResourceType resourceType) throws IOException {
        Path path = getResourcePath(resourceId, resourceType.getResourceName());
        return Files.deleteIfExists(path);
    }


    public Path getRelative(Path path){
        return managedDirectory.relativize(path);
    }

    public Path getResourcePath(UUID resourceId, String resourceName){
        return Paths.get(managedDirectory.toAbsolutePath().toString(), resourceId.toString(), resourceName);
    }

    protected Path getDirectory(String dir) throws IOException {
        Path path = Paths.get(dir);
        if (!Files.exists(path) || !Files.isDirectory(path)){
            //TODO: do we need to specify attributes?
            Files.createDirectory(path);
        }
        return path;
    }

    protected Path getDirectory(Path base, String dirName) throws IOException {
        Path projectPath = Paths.get(base.toString(), dirName);
        return getDirectory(projectPath.toString());
    }



    public void removeAllResources(UUID resourceId) throws IOException {
        Path resourceDirectory = Paths.get(managedDirectory.toAbsolutePath().toString(), resourceId.toString());
        Files.walk(resourceDirectory).
                sorted((a, b) -> b.compareTo(a)). // reverse; files before dirs
                forEach(p -> {
            try {
                Files.delete(p);
            } catch (IOException e) {
                log.error("Problem deleting resource: {}", e);
            }
        });
    }

}
