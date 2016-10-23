package org.digitalsprouts.recipesearchclient.model;

final class MockData {

    static Recipe getMockRecipe() {
        Recipe mock = new Recipe();
        mock.setTitle("title");
        mock.setThumbnailUrl("http://host.tld/path/image.ext");
        mock.setSourceUrl("http://otherhost.tld/path/");
        mock.setPublisherUrl("http://otherhost.tld");
        mock.setPublisher("publisher");
        mock.setRecipeUrl("http://host.tld/path/");
        mock.setSocialRank(99.5);

        return mock;
    }

}
