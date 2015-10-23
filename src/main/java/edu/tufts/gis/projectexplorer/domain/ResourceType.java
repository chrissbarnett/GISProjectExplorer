package edu.tufts.gis.projectexplorer.domain;

/**
 * Created by cbarne02 on 5/1/15.
 */
public enum ResourceType {
    poster_full_pdf("poster.pdf"),
    thumbnail_png("thumbnail.png"),
    paper_pdf("paper.pdf"),
    paper_doc("paper.doc"),
    paper_docx("paper.docx");

    private final String resource_name;

    ResourceType(String resource_name){
        this.resource_name = resource_name;
    }

    public String getResourceName(){
        return this.resource_name;
    }

    public static ResourceType getPaperType(String format) throws Exception {
        if (format.equalsIgnoreCase("pdf")){
            return ResourceType.paper_pdf;
        } else if (format.equalsIgnoreCase("doc")){
            return ResourceType.paper_doc;
        } else if (format.equalsIgnoreCase("docx")){
            return ResourceType.paper_docx;
        } else {
            throw new Exception("Unknown paper type ['" + format + "']");
        }
    }

    public boolean isPaper(){
        if (this.equals(ResourceType.paper_doc) || this.equals(ResourceType.paper_docx) ||
                this.equals(ResourceType.paper_pdf)){
            return true;
        }
        return false;
    }

    public boolean isPoster(){
        if (this.equals(ResourceType.poster_full_pdf)){
            return true;
        }
        return false;
    }

    public static ResourceType parse(String resourceName) throws Exception {
        for (ResourceType rt: ResourceType.values()){
            if (resourceName.equalsIgnoreCase(rt.getResourceName())){
                return rt;
            }
        }

        throw new Exception("Unrecognized ResourceType!");
    }

    public static ResourceType lazyParse(String resourceType$, boolean isThumb) throws Exception {
        ResourceType resourceType = null;
        if (isThumb){
            resourceType = ResourceType.thumbnail_png;
        } else if (resourceType$.equalsIgnoreCase("paper")){
            resourceType = ResourceType.paper_pdf;
        } else if (resourceType$.equalsIgnoreCase("poster")){
            resourceType = ResourceType.poster_full_pdf;
        } else {
            throw new Exception("Unable to guess ResourceType!");
        }
        return resourceType;
    }
}
